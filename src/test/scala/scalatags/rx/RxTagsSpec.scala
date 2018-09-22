package scalatags.rx

import org.querki.jquery._
import org.scalactic.Equality._
import org.scalactic.StringNormalizations._
import org.scalajs.dom.Node
import rx.Ctx.Owner.Unsafe._
import rx._
import scalatags.JsDom.all._
import scalatags.UnitSpec
import tags._

class RxTagsSpec extends UnitSpec {

  "Tag" should "bind text" in {
    val el = div {
      "a"
    }.render

    $(el).text() should equal("a")(after being lowerCased)
  }

  it should "bind Rx text" in {
    val v = Var("a")
    val el = div {
      Rx {
        v() + "1"
      }
    }.render

    $(el).text() should equal("a1")

    v() = "b"
    $(el).text() should equal("b1")

    v() = "c"
    $(el).text() should equal("c1")
  }

  it should "bind Rx number" in {
    val v = Var(1)
    val el = div {
      Rx {
        v() + 1
      }
    }.render

    $(el).text() should equal("2")

    v() = 2
    $(el).text() should equal("3")
  }

  it should "bind Rx boolean" in {
    val v = Var(true)
    val el = div {
      Rx {
        v()
      }
    }.render

    $(el).text() should equal("true")

    v() = false
    $(el).text() should equal("false")
  }

  it should "bind Rx node" in {
    val v = Var("a")
    val el = div {
      Rx {
        p(v() + "1").render
      }
    }.render

    $("p", el).text() should equal("a1")

    v() = "b"
    $(el).text() should equal("b1")

    v() = "c"
    $(el).text() should equal("c1")
  }

  it should "bind Rx frag" in {
    val v = Var("a")
    val el = div {
      Rx {
        p(v() + "1")
      }
    }.render

    $("p", el).text() should equal("a1")

    v() = "b"
    $(el).text() should equal("b1")

    v() = "c"
    $(el).text() should equal("c1")
  }

  it should "bind to frag sequence" in {
    val rx_seq = Var((1 to 3).map(p(_)))

    val el = div(rx_seq).render
    for (i <- 1 to 3) {
      $("p", el)(i - 1).textContent should equal(i.toString)
    }

    rx_seq() = (4 to 6).map(p(_))
    for (i <- 4 to 6)
      $("p", el)(i - 4).textContent should equal(i.toString)
  }

  it should "replace deeply nested dom structure" in {
    val seq = Var {
      Seq(
        p(p(p(p(1)))),
        p(p(p(p(2)))),
        p(p(p(p(3))))
      )
    }
    val el = div(seq).render

    $("p p p p:eq(0)", el).text() should equal("1")
    $("p p p p:eq(1)", el).text() should equal("2")
    $("p p p p:eq(2)", el).text() should equal("3")
    el.childElementCount should ===(3)

    seq() = Seq(
      p(p(p(p(4)))),
      p(p(p(p(5)))),
      p(p(p(p(6))))
    )

    $("p p p p:eq(0)", el).text() should equal("4")
    $("p p p p:eq(1)", el).text() should equal("5")
    $("p p p p:eq(2)", el).text() should equal("6")
    el.childElementCount should ===(3)

    seq() = Seq(
      p(p(p(p(4)))),
      p(p(p(p(6))))
    )

    $("p p p p:eq(0)", el).text() should equal("4")
    $("p p p p:eq(1)", el).text() should equal("6")
    el.childElementCount should ===(2)

    seq() = Seq(
      p(1)
    )

    $("p:eq(0)", el).text() should equal("1")
    el.childElementCount should ===(1)

  }

  it should "render if empty list" in {
    val el = div(Var(Seq.empty[Frag])).render
    el.childElementCount should ===(0)

    val el2 = div(Var(Seq.empty[Node])).render
    el2.childElementCount should ===(0)
  }

  it should "render seq of values" in {
    val v = Var(1 to 10)
    val d = div(v)
    d.toString() should equal(div(1 to 10).toString)

    v() = 20 to 40
    d.toString() should equal(div(20 to 40).toString)
  }

  it should "render option" in {
    val vars = Var(Option(1).toSeq)
    val el = div(vars).render

    $(el).text() should equal("1")

    vars() = Option(2).toSeq
    $(el).text() should equal("2")

    vars() = None.toSeq
    $(el).text() should equal("")
  }

}
