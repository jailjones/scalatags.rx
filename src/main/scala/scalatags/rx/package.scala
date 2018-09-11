package scalatags

package object rx {

  val tags = RxTags
  val attrs = RxAttrs
  val styles = RxStyles

  object all extends RxTags with RxAttrs with RxStyles
}
