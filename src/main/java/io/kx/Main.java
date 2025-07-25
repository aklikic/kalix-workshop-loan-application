package io.kx;

import io.kx.loanapp.action.LoanAppApiControllerAction;
import io.kx.loanapp.action.LoanAppEventingToProcAction;
import io.kx.loanapp.domain.LoanAppEntity;
import io.kx.loanproc.action.LoanProcEventingToAppAction;
import io.kx.loanproc.action.LoanProcTimeoutAction;
import io.kx.loanproc.domain.LoanProcEntity;
import io.kx.loanproc.view.LoanProcByStatusView;
import kalix.javasdk.Kalix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public final class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static Kalix createKalix() {
    // The KalixFactory automatically registers any generated Actions, Views or Entities,
    // and is kept up-to-date with any changes in your protobuf definitions.
    // If you prefer, you may remove this and manually register these components in a
    // `new Kalix()` instance.
    return KalixFactory.withComponents(LoanAppEntity::new, LoanProcEntity::new,  LoanAppApiControllerAction::new, LoanAppEventingToProcAction::new, LoanProcByStatusView::new, LoanProcEventingToAppAction::new, LoanProcTimeoutAction::new);
  }

  public static void main(String[] args) throws Exception {
    LOG.info("starting the Kalix service");
    createKalix().start();
  }
}
