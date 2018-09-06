package scalatags.rx

import org.scalajs.dom.html.Div
import rx._
import scalatags.JsDom.all._
import utest._
import scalatags.rx.Implicits._

object RxElementTests extends TestSuite {

  val tests = Tests {
    "bind frag" - {

      Rx {
        val v = Var("a")
        val rx = Rx {
          p(v())
        }

        val el: Div = div(rx).render
        assert(el.firstElementChild.tagName equalsIgnoreCase "p")
        assert(el.firstElementChild.textContent equals "a")

        v() = "b"
        assert(el.firstElementChild.tagName equalsIgnoreCase "p")
        assert(el.firstElementChild.textContent equals "b")
      }
    }

    "bind dom element" - {

      Rx {
        val v = Var("a")
        val rx = Rx {
          p(v()).render
        }

        val el: Div = div(rx).render

        assert(el.firstElementChild != null)
        assert(el.firstElementChild.tagName equalsIgnoreCase "p")
        assert(el.firstElementChild.textContent equals "a")
        v() = "b"

        assert(el.firstElementChild.textContent equals "b")
        assert(el.firstElementChild.tagName equalsIgnoreCase "p")
        assert(el.firstElementChild.textContent equals "b")

      }
    }

    "bind text node" - {
      Rx {
        val v = Var("1")
        val el = p(Rx(v())).render

        assert(el.textContent equals ("1"))

        v() = "2"
        assert(el.textContent equals ("2"))
      }
    }

    "bind int node" - {
      Rx {
        val v = Var(1)
        val el = p(Rx(v())).render
        assert(el.textContent equals ("1"))

        v() = 2
        assert(el.textContent equals ("2"))
      }
    }
  }
}
