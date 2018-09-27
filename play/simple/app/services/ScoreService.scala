package services

import models.{Score, Scores}
import scala.concurrent.Future

object ScoreService {

  def listAllScores: Future[Seq[Score]] = {
    Scores.listAll
  }
}