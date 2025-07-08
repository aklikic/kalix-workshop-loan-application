package io.kx.loanproc.action;

import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.kx.loanproc.api.LoanProcApi;
import io.kx.loanproc.domain.LoanProcDomain;
import kalix.javasdk.action.ActionCreationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletionStage;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Action Service described in your io/kx/loanproc/action/loan_proc_timeout_action.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class LoanProcTimeoutAction extends AbstractLoanProcTimeoutAction {

  private static final Logger logger = LoggerFactory.getLogger(LoanProcTimeoutAction.class);

  private static final int TIMEOUT = 5;
  public LoanProcTimeoutAction(ActionCreationContext creationContext) {}

  @Override
  public Effect<Empty> onReadyForReview(LoanProcDomain.ReadyForReview readyForReview) {
    var declineReq = LoanProcApi.DeclineCommand.newBuilder()
            .setLoanAppId(readyForReview.getLoanAppId())
            .setReason("Timeout after: "+TIMEOUT)
            .setReviewerId("TIMEOUT")
            .build();
    var deferredCall = components().loanProcEntity().decline(declineReq);
    var timer = timers()
            .startSingleTimer(readyForReview.getLoanAppId(), Duration.of(TIMEOUT, ChronoUnit.SECONDS),deferredCall);
    return effects().asyncReply(timer.thenApply(response -> Empty.getDefaultInstance()));
  }
  @Override
  public Effect<Empty> onApproved(LoanProcDomain.Approved approved) {
    return effects().asyncReply(timers().cancel(approved.getLoanAppId()).thenApply(response -> Empty.getDefaultInstance()));
  }
  @Override
  public Effect<Empty> onDeclined(LoanProcDomain.Declined declined) {
    return effects().asyncReply(timers().cancel(declined.getLoanAppId()).thenApply(response -> Empty.getDefaultInstance()));
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().ignore();
  }
}
