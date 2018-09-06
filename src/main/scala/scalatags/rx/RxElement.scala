package scalatags.rx

import java.util.concurrent.atomic.AtomicReference

import org.scalajs.dom._
import rx._
import scalatags.JsDom.all._

import scala.languageFeature.implicitConversions

trait RxElement {

  implicit def bindData[T](rx: Rx[T])(implicit f: T => Frag, ctx: Ctx.Owner): Frag = rx.map(f)

  implicit class bindFrag[T <: Frag](rx: Rx[T])(implicit ctx: Ctx.Owner) extends Frag {

    private lazy val rxNode = rx.map(_.render)

    override def render: Node = rxNode.now

    override def applyTo(el: Element): Unit = {
      val ref = new AtomicReference(render)
      el.appendChild(ref.get)
      rxNode.triggerLater { current =>
        val previous = ref.getAndSet(current)
        el.replaceChild(current, previous)
      }
    }
  }

}
