package scalatags.rx

import org.scalajs.dom.Element
import rx._
import scalatags.JsDom._
import scala.languageFeature.implicitConversions

trait RxAttr {

  implicit def bindVarToAttr[T](implicit av: AttrValue[T], ctx: Ctx.Owner): RxAttrValue[T, Var[T]] = new RxAttrValue

  implicit def bindRxToAttr[T](implicit av: AttrValue[T], ctx: Ctx.Owner): RxAttrValue[T, Rx[T]] = new RxAttrValue

  implicit def bindRxDynamicToAttr[T](implicit av: AttrValue[T], ctx: Ctx.Owner): RxAttrValue[T, Rx.Dynamic[T]] = new RxAttrValue

  class RxAttrValue[T, U <: Rx[T]](implicit av: AttrValue[T], ctx: Ctx.Owner) extends AttrValue[U] {
    override def apply(t: Element, a: Attr, rxv: U): Unit = rxv foreach (av(t, a, _))
  }

}




