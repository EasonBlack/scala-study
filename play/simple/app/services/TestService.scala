package services

import com.google.inject.{ImplementedBy, Inject, Singleton}


@Singleton
class TestService @Inject() {
  private var count: Int = 0

  def addCount() {
    count = count + 1;
  }

  def minusCount() {
    count = count - 1;
  }

  def showCount(): Int  = count
}