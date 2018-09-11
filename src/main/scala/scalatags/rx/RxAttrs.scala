package scalatags.rx

import org.scalajs.dom.Element
import rx._
import scalatags.JsDom._

object RxAttrs extends RxAttrs

trait RxAttrs {

  implicit def RxAttrValue[A: AttrValue](implicit ctx: Ctx.Owner): RxAttrValue[A, Rx[A]] = new RxAttrValue

  implicit def VarAttrValue[A: AttrValue](implicit ctx: Ctx.Owner): RxAttrValue[A, Var[A]] = new RxAttrValue

  implicit def RxDynamicAttrValue[A: AttrValue](implicit ctx: Ctx.Owner): RxAttrValue[A, Rx.Dynamic[A]] = new RxAttrValue

  final class RxAttrValue[A, R <: Rx[A]](implicit av: AttrValue[A], ctx: Ctx.Owner) extends AttrValue[R] {
    override def apply(t: Element, a: Attr, rx: R): Unit = for (v <- rx) av(t, a, v)
  }

}

