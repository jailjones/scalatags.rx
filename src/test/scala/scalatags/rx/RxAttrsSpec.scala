package scalatags.rx

import org.querki.jquery._
import org.scalactic.Equality._
import rx.Ctx.Owner.Unsafe._
import rx._
import scalatags.JsDom.all._
import scalatags.rx.attrs._

class RxAttrsSpec extends UnitSpec {

  "Attr" should "bind to string" in {
    val v = Var("a")

    Rx {
      val el = div(cls := v()).render
      $(el).attr("class").get should equal("a")

      v() = "b"
      $(el).attr("class").get should equal("b")
    }
  }

  it should "bind to number" in {
    val v = Var(1)
    val el = div(id := Rx(v())).render
    $(el).attr("id").get should equal("1")

    v() = 2
    $(el).attr("id").get should equal("2")
  }

  it should "bind to boolean" in {
    val v = Var(true)
    val el = div(id := Rx(v())).render

    $(el).attr("id").get should equal("true")

    v() = false
    $(el).attr("id").get should equal("false")
  }


}