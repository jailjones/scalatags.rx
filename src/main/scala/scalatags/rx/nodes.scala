package scalatags.rx

import java.util.concurrent.atomic.AtomicReference

import org.scalajs.dom._
import rx._
import scalatags.JsDom._

/**
  * Implicits for binding Scalatags [[https://github.com/lihaoyi/scalatags]] nodes
  * to scala.rx [[https://github.com/lihaoyi/scala.rx]] variables
  */
trait nodes {

  implicit def rxFrag(rx: Rx[Frag])(implicit ctx: Ctx.Owner): rxNode[Node] = rx.map(_.render)

  implicit class rxNode[N <: Node](rx: Rx[N])(implicit ctx: Ctx.Owner) extends Frag {

    override def render: N = rx.now

    override def applyTo(el: Element): Unit = {
      val ref = new AtomicReference(render)
      el.appendChild(ref.get)
      rx.triggerLater { current =>
        val prev = ref.getAndSet(current)
        el.replaceChild(ref.get, prev)
      }
    }
  }

  //implicit def rxOption[A](rx: Rx[Option[A]]): rxElementTraversable = rx.map(_.toSeq)

  implicit def rxFragTraversable[A](rx: Rx[Traversable[A]])(implicit ev: A => Frag, ctx: Ctx.Owner): rxElementTraversable[A] =
    rxElementTraversable(rx)(a => ev(a).render, ctx)

  implicit class rxElementTraversable[A](rx: Rx[Traversable[A]])(implicit ev: A => Node, ctx: Ctx.Owner) extends Frag {

    override def render: DocumentFragment = {
      val frag = document.createDocumentFragment()
      rx.now.foreach(a => frag.appendChild(ev(a)))
      frag
    }

    override def applyTo(el: Element): Unit = {
      val ref = new AtomicReference(rx.now)
      el.appendChild(render)
      rx.triggerLater { current =>
        // Naive implementation - does a complete remove and replace of all nodes.  May need to do switch
        // to an incremental add/replace/remove strategy if rendering is janky
        val prev = ref.getAndSet(current)
        prev.foreach(a => el.removeChild(ev(a)))
        el.appendChild(render)
      }
    }
  }

  //  implicit class SeqRxNode[A](rx: Rx[Traversable[A]])(implicit ev: A => Modifier, ctx: Ctx.Owner) extends Modifier {
  //    override def applyTo(t: Element): Unit = {
  //      val ref = new AtomicReference(rx.now)
  //      val xs = ref.get
  //      xs.foreach(_.applyTo(t))
  //      rx.triggerLater { current =>
  //        val prev = ref.getAndSet(current)
  //        t.replaceChild(ref.get, prev)
  //      }
  //    }
  //  }

  implicit def rxBoolean(rx: Rx[Boolean])(implicit ctx: Ctx.Owner): rxString = rx.map(_.toString)

  implicit def rxNumeric[N: Numeric](rx: Rx[N])(implicit ctx: Ctx.Owner): rxString = rx.map(_.toString)

  implicit class rxString(rx: Rx[String])(implicit ctx: Ctx.Owner) extends Frag {

    override def render: Text = {
      val txt = document.createTextNode(rx.now)
      rx.foreach { s =>
        txt.replaceData(0, txt.length, s)
      }
      txt
    }

    override def applyTo(t: Element): Unit = t.appendChild(render)
  }

}

object nodes extends nodes
