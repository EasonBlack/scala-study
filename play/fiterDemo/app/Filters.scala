


import javax.inject._

import filters.{LoggingFilter}
import play.api.http.DefaultHttpFilters


@Singleton
class Filters @Inject()(loggingFilter: LoggingFilter)
  extends DefaultHttpFilters(loggingFilter) {
}
