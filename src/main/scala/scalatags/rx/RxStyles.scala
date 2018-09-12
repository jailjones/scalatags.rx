package scalatags.rx

import org.scalajs.dom.Element
import rx._
import scalatags.JsDom.all._
import scalatags.generic.StylePair

object RxStyles extends RxStyles

trait RxStyles extends RxDataConverter {

  implicit def RxStyleValue[A: StyleValue](implicit ctx: Ctx.Owner): RxStyleValue[A, Rx[A]] = new RxStyleValue

  implicit def VarStyleValue[A: StyleValue](implicit ctx: Ctx.Owner): RxStyleValue[A, Var[A]] = new RxStyleValue

  implicit def RxDynamicStyleValue[A: StyleValue](implicit ctx: Ctx.Owner): RxStyleValue[A, Rx.Dynamic[A]] =
    new RxStyleValue

  final class RxStyleValue[A, R <: Rx[A]](implicit sv: StyleValue[A], ctx: Ctx.Owner) extends StyleValue[R] {
    override def apply(t: Element, s: Style, rx: R): Unit =
      rx.trigger(sv(t, s, _))
  }

}

trait RxDataConverter {

  implicit def VarPixelStyleValue[A](implicit ev: StyleValue[Var[A]], ctx: Ctx.Owner): RxPixelStyleValue[A, Var[A]] =
    new RxPixelStyleValue(ev)

  implicit def RxDynamicPixelStyleValue[A](implicit ev: StyleValue[Rx.Dynamic[A]],
                                           ctx: Ctx.Owner): RxPixelStyleValue[A, Rx.Dynamic[A]] =
    new RxPixelStyleValue(ev)

  implicit def RxPixelStyleValue[A](implicit ev: StyleValue[Rx[A]], ctx: Ctx.Owner): RxPixelStyleValue[A, Rx[A]] =
    new RxPixelStyleValue(ev)

  final class RxPixelStyleValue[A, R <: Rx[A]](sv: StyleValue[R])(implicit ctx: Ctx.Owner) extends PixelStyleValue[R] {
    override def apply(s: Style, v: R): StylePair[Element, _] =
      StylePair(s, v, sv)
  }

  final class RxPixelStyleValuePx(sv: StyleValue[Rx[String]])(implicit ctx: Ctx.Owner) extends PixelStyleValue[Rx[String]] {
    override def apply(s: Style, v: Rx[String]): StylePair[Element, _] =
      StylePair(s, v.map(_ + "px"), sv)
  }

}
