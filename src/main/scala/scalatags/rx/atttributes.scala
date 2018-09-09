package scalatags.rx

import org.scalajs.dom.Element
import rx._
import scalatags.JsDom._

trait atttributes {

  //implicit def bindRxAttr[T](implicit av: AttrValue[T], ctx: Ctx.Owner): RxAttrValue[T, Var[T]] = new RxAttrValue

  implicit def bindRxAttr[T](implicit av: AttrValue[T], ctx: Ctx.Owner): RxAttrValue[T, Rx[T]] = new RxAttrValue

  //
  //  implicit def bindRxDynamicToAttr[T](implicit av: AttrValue[T], ctx: Ctx.Owner): RxAttrValue[T, Rx.Dynamic[T]] = new RxAttrValue

  final class RxAttrValue[T, U <: Rx[T]](implicit av: AttrValue[T], ctx: Ctx.Owner) extends AttrValue[U] {
    override def apply(t: Element, a: Attr, rxv: U): Unit = rxv foreach (av(t, a, _))
  }

}

object atttributes extends atttributes


