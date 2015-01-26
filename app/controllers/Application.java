package controllers;

import static play.data.Form.form;
import models.AccountData;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

  public static Result index() {

    return ok(index.render("Your new application is ready.", form(BankForm.class)));
  }

  public static Result getIban() {
    String iban = "";
    Form<BankForm> fbf = form(BankForm.class).bindFromRequest();
    if (fbf == null) {
      return badRequest(index.render("da ist was schief gelaufen", form(BankForm.class)));
    }
    if (fbf.hasErrors()) {
      return badRequest(index.render("ungueltige Angaben", form(BankForm.class)));
    } else {
      BankForm bf = fbf.get();
      if (bf == null) {
        return badRequest(index.render("da ist was schief gelaufen", form(BankForm.class)));
      }
      AccountData ad = new AccountData(bf);
      if (ad == null) {
        return badRequest(index.render("da ist was schief gelaufen", form(BankForm.class)));
      }
      iban = ad.getIban();
      return ok(index.render(iban, form(BankForm.class)));
    }
  }

  public static class BankForm {
    @Constraints.Required
    @Constraints.MaxLength(10)
    public String accountno;
    @Constraints.Required
    @Constraints.MaxLength(8)
    @Constraints.MinLength(8)
    public String blz;


    public String validate() {
      try {
        if (new Long(accountno) > 9999999999L) {
          return "Keine gueltige Kontonr";
        }
      } catch (NumberFormatException n) {
        return "Keine Nummer";
      }
      try {
        if (new Long(blz) > 99999999L) {
          return "Keine gueltige BLZ";
        }
      } catch (NumberFormatException n) {
        return "BLZ keine Nummer";
      }
      return null;
    }
  }
}
