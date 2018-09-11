package scalatags.rx

import org.querki.jquery._
import org.scalactic.Equality._
import rx.Ctx.Owner.Unsafe._
import rx._
import scalatags.JsDom.all._
import styles._

class RxStyleSpec extends UnitSpec {

  "Style" should "render normally" in {
    val el = div(color := "red").render

    $(el).css("color") should equal("red")
  }

  it should "render Var string" in {
    Rx {
      val v = Var("red")
      val el = div(color := v).render
      $(el).css("color") should equal("red")

      v() = "blue"
      $(el).css("color") should equal("blue")
    }
  }

  it should "render Rx string" in {
    val v = Var("red")
    val el = div(color := Rx(v)).render
    $(el).css("color") should equal("red")

    v() = "blue"
    $(el).css("color") should equal("blue")

  }

  it should "render Var int" in {
    Rx {
      val v = Var(1)
      val el = div(margin := v()).render
      $(el).css("margin") should equal("1")

      v() = 2
      $(el).css("margin") should equal("2")
    }
  }

  it should "render pixel value outside var" in {
    Rx {
      val v = Var(1)
      val el = div(margin := v().px).render
      $(el).css("margin") should equal("1px")

      v() = 2
      $(el).css("margin") should equal("2px")
    }
  }

  it should "render pixel value inside var" in {
    Rx {
      val v = Var(1.px)
      val el = div(margin := v()).render
      $(el).css("margin") should equal("1px")

      v() = 2.px
      $(el).css("margin") should equal("2px")
    }
  }

  it should "render decimal pixel value" in {
    Rx {
      val v = Var(1.1.px)
      val el = div(margin := v()).render
      $(el).css("margin") should equal("1.1px")

      v() = 2.2.px
      $(el).css("margin") should equal("2.2px")
    }
  }

  it should "render perform pixel math" in {
    val v = Var(1)
    val v2 = Var(1)
    val el = div(margin := Rx(v() + v2() px)).render
    $(el).css("margin") should equal("2px")

    v() = 2
    $(el).css("margin") should equal("3px")


  }


}

