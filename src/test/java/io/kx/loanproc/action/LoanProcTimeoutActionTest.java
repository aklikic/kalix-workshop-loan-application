package io.kx.loanproc.action;

import akka.stream.javadsl.Source;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.kx.loanproc.action.LoanProcTimeoutAction;
import io.kx.loanproc.action.LoanProcTimeoutActionTestKit;
import io.kx.loanproc.domain.LoanProcDomain;
import kalix.javasdk.testkit.ActionResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class LoanProcTimeoutActionTest {

  @Test
  @Disabled("to be implemented")
  public void exampleTest() {
    LoanProcTimeoutActionTestKit service = LoanProcTimeoutActionTestKit.of(LoanProcTimeoutAction::new);
    // // use the testkit to execute a command
    // SomeCommand command = SomeCommand.newBuilder()...build();
    // ActionResult<SomeResponse> result = service.someOperation(command);
    // // verify the reply
    // SomeReply reply = result.getReply();
    // assertEquals(expectedReply, reply);
  }

  @Test
  @Disabled("to be implemented")
  public void onReadyForReviewTest() {
    LoanProcTimeoutActionTestKit testKit = LoanProcTimeoutActionTestKit.of(LoanProcTimeoutAction::new);
    // ActionResult<Empty> result = testKit.onReadyForReview(LoanProcDomain.ReadyForReview.newBuilder()...build());
  }

  @Test
  @Disabled("to be implemented")
  public void onApprovedTest() {
    LoanProcTimeoutActionTestKit testKit = LoanProcTimeoutActionTestKit.of(LoanProcTimeoutAction::new);
    // ActionResult<Empty> result = testKit.onApproved(LoanProcDomain.Approved.newBuilder()...build());
  }

  @Test
  @Disabled("to be implemented")
  public void onDeclinedTest() {
    LoanProcTimeoutActionTestKit testKit = LoanProcTimeoutActionTestKit.of(LoanProcTimeoutAction::new);
    // ActionResult<Empty> result = testKit.onDeclined(LoanProcDomain.Declined.newBuilder()...build());
  }

  @Test
  @Disabled("to be implemented")
  public void ignoreOtherEventsTest() {
    LoanProcTimeoutActionTestKit testKit = LoanProcTimeoutActionTestKit.of(LoanProcTimeoutAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
