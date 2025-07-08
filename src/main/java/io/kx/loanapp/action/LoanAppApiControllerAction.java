package io.kx.loanapp.action;

import com.google.protobuf.Empty;
import io.kx.loanapp.api.LoanAppApi;
import kalix.javasdk.action.ActionCreationContext;

import java.util.UUID;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Action Service described in your io/kx/loanapp/action/loan_app_controller_action.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class LoanAppApiControllerAction extends AbstractLoanAppApiControllerAction {

  public LoanAppApiControllerAction(ActionCreationContext creationContext) {}

  @Override
  public Effect<LoanAppApiControllerHandler.SubmitResponse> submit(LoanAppApiControllerHandler.SubmitCommand submitCommand) {
    var loanAppId = UUID.randomUUID().toString();
    var loanAppEntitySubmitCommand =
            LoanAppApi.SubmitCommand.newBuilder()
                    .setLoanAppId(loanAppId)
                    .setClientId(submitCommand.getClientId())
                    .setLoanAmountCents(submitCommand.getLoanAmountCents())
                    .setLoanDurationMonths(submitCommand.getLoanDurationMonths())
                    .setClientMonthlyIncomeCents(submitCommand.getClientMonthlyIncomeCents())
                    .build();
    var reply = LoanAppApiControllerHandler.SubmitResponse.newBuilder().setLoanAppId(loanAppId).build();
    return effects().asyncReply(components().loanAppEntity().submit(loanAppEntitySubmitCommand).execute().thenApply(e-> reply));
  }
  @Override
  public Effect<LoanAppApi.LoanAppState> get(LoanAppApi.GetCommand getCommand) {
    return effects().forward(components().loanAppEntity().get(getCommand));
  }
  @Override
  public Effect<Empty> approve(LoanAppApi.ApproveCommand approveCommand) {
   return effects().forward(components().loanAppEntity().approve(approveCommand));
  }
  @Override
  public Effect<Empty> decline(LoanAppApi.DeclineCommand declineCommand) {
    return effects().forward(components().loanAppEntity().decline(declineCommand));
  }
}
