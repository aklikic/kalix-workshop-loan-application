package io.kx.loanapp.action;

import akka.stream.javadsl.Source;
import com.google.protobuf.Empty;
import io.kx.loanapp.action.LoanAppApiControllerAction;
import io.kx.loanapp.action.LoanAppApiControllerActionTestKit;
import io.kx.loanapp.api.LoanAppApi;
import kalix.javasdk.testkit.ActionResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class LoanAppApiControllerActionTest {

  @Test
  @Disabled("to be implemented")
  public void exampleTest() {
    LoanAppApiControllerActionTestKit service = LoanAppApiControllerActionTestKit.of(LoanAppApiControllerAction::new);
    // // use the testkit to execute a command
    // SomeCommand command = SomeCommand.newBuilder()...build();
    // ActionResult<SomeResponse> result = service.someOperation(command);
    // // verify the reply
    // SomeReply reply = result.getReply();
    // assertEquals(expectedReply, reply);
  }

  @Test
  @Disabled("to be implemented")
  public void submitTest() {
    LoanAppApiControllerActionTestKit testKit = LoanAppApiControllerActionTestKit.of(LoanAppApiControllerAction::new);
    // ActionResult<Empty> result = testKit.submit(LoanAppApi.SubmitCommand.newBuilder()...build());
  }

  @Test
  @Disabled("to be implemented")
  public void getTest() {
    LoanAppApiControllerActionTestKit testKit = LoanAppApiControllerActionTestKit.of(LoanAppApiControllerAction::new);
    // ActionResult<LoanAppApi.LoanAppState> result = testKit.get(LoanAppApi.GetCommand.newBuilder()...build());
  }

  @Test
  @Disabled("to be implemented")
  public void approveTest() {
    LoanAppApiControllerActionTestKit testKit = LoanAppApiControllerActionTestKit.of(LoanAppApiControllerAction::new);
    // ActionResult<Empty> result = testKit.approve(LoanAppApi.ApproveCommand.newBuilder()...build());
  }

  @Test
  @Disabled("to be implemented")
  public void declineTest() {
    LoanAppApiControllerActionTestKit testKit = LoanAppApiControllerActionTestKit.of(LoanAppApiControllerAction::new);
    // ActionResult<Empty> result = testKit.decline(LoanAppApi.DeclineCommand.newBuilder()...build());
  }

}
