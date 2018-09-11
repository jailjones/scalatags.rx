package scalatags.rx

import java.util.concurrent.atomic.AtomicReference

import org.scalajs.dom._
import rx._
import scalatags.JsDom._

object RxTags extends RxTags

trait RxTags extends LowPriorityRxTagImplicits {

  implicit def booleanToString(b: Boolean): String = b.toString

  implicit def numericToString[A](b: Numeric[A]): String = b.toString

  implicit class RxText[A](val rx: Rx[A])(implicit ev: A => String, ctx: Ctx.Owner) extends Frag {

    override def render: Text = {
      val text = document.createTextNode(rx.now)
      rx.foreach { s =>
        text.replaceData(0, text.length, s)
      }
      text
    }

    override def applyTo(t: Element): Unit = t.appendChild(render)

  }

}

/**
  * Implicits for binding Scalatags [[https://github.com/lihaoyi/scalatags]] nodes
  * to scala.rx [[https://github.com/lihaoyi/scala.rx]] variables
  */
trait LowPriorityRxTagImplicits {

  implicit class RxFrag[A](val rx: Rx[A])(implicit ev: A => Frag, ctx: Ctx.Owner) extends Frag {

    override def render: Node = rx.now.render

    override def applyTo(el: Element): Unit = {
      val ref = new AtomicReference(render)
      el.appendChild(ref.get)
      rx.triggerLater { now =>
        val prev = ref.getAndSet(now.render)
        el.replaceChild(ref.get, prev)
      }
    }
  }

  implicit class RxTraversableFrag[A](val rx: Rx[Traversable[A]])(implicit ev: A => Frag, ctx: Ctx.Owner) extends Frag {

    private implicit def toNodes(t: Traversable[A]): Traversable[Node] = t.map(ev(_).render)

    private implicit def toFrag(t: Traversable[Node]): DocumentFragment = {
      val frag = document.createDocumentFragment()
      t.foreach(frag.appendChild)
      frag
    }

    override def render: DocumentFragment = rx.now: Traversable[Node]

    override def applyTo(t: Element): Unit = {
      val ref = new AtomicReference[Traversable[Node]](rx.now)
      t.appendChild(toFrag(ref.get))
      rx.triggerLater { current =>
        val prevNodes = ref.getAndSet(current)
        prevNodes.foreach(t.removeChild)
        val curNodes = ref.get
        curNodes.foreach(t.appendChild)
      }
    }
  }

}