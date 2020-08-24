package example


import books.books.{BookList, Empty}
import books.books.BookServiceGrpc
import com.github.phisgr.gatling.grpc.Predef.{$, _}
import com.github.phisgr.gatling.pb._
import io.grpc.ManagedChannelBuilder
import io.gatling.core.Predef.{stringToExpression => _, _}
import io.gatling.core.Predef._
import scala.concurrent.duration._
import io.gatling.core.session.Expression


class ExampleSimulation extends Simulation {

  val grpcConf = grpc(ManagedChannelBuilder.forAddress("185.233.0.230", 50051).usePlaintext())

  val callBook: Expression[Empty] = Empty()
    .updateExpr()

  val bookListCall = grpc("bookListCall")
      .rpc(books.books.BookServiceGrpc.METHOD_LIST)
      .payload(callBook)

  val bookScenario = scenario("BookList")
      .exec(bookListCall)

  setUp(bookScenario.inject(constantUsersPerSec(50) during(15 minutes)).protocols(grpcConf))

}
