package uo.ri.cws.application.service.contract;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.common.LocalDateConverter;
import uo.ri.cws.application.service.common.TestContext;
import uo.ri.cws.application.service.common.TestContext.Key;
import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.cws.application.service.util.ContractUtil;
import uo.ri.cws.application.service.util.sql.FindContractByIdSqlUnitOfWork;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.*;

public class UpdateSteps {
    private final ContractService service =
            Factory.service.forContractService();
    private final TestContext ctx;
    private ContractDto c;
    private ContractDto after = null, before = null;

    public UpdateSteps(TestContext ctx) {
        this.ctx = ctx;
    }

    @When("I try to update a contract with null arg")
    public void i_try_to_update_a_contract_with_null_arg() {
        tryUpdateAndKeepException(null);
    }

    @When("I try to update a contract with null id")
    public void i_try_to_update_a_contract_with_null_id() {
        c = new ContractUtil().unique().withId(null).get();
        tryUpdateAndKeepException(c);

    }

    @When("I try to update a contract with id {string}")
    public void i_try_to_update_a_contract_with_id(String arg) {
        c = new ContractUtil().unique().withId(arg).get();
        tryUpdateAndKeepException(c);
    }


    @When("I try to update a contract with wrong wage {double}")
    public void i_try_to_update_a_contract_with_wrong_wage(Double money) {
        c = new ContractUtil().unique().withAnnualWage(money).get();
        tryUpdateAndKeepException(c);
    }

    @When("I try to update a non existing contract")
    public void i_try_to_update_a_non_existing_contract()
            throws BusinessException {
        ContractUtil cutil = new ContractUtil().unique()
                .withId(UUID.randomUUID().toString());
        c = cutil.get();
        tryUpdateAndKeepException(c);
    }

    @When("I try to update a terminated contract")
    public void i_try_to_update_terminated_contract() {

        c = (ContractDto) ctx.get(Key.TERMINATED);
        tryUpdateAndKeepException(c);

    }

    @When("I try to update a fixed term contract with end date earlier than " +
            "start date")
    public void i_try_to_update_fixed_term_contract_inforce() {

        c = (ContractDto) ctx.get(Key.INFORCE);
        c.endDate = c.startDate.minusDays(1);
        tryUpdateAndKeepException(c);

    }


    @When("I try to update a contract in force")
    public void i_try_to_update_contract_inforce() {

        c = (ContractDto) ctx.get(Key.INFORCE);
        tryUpdateAndKeepException(c);

    }


    @When("I try to update the contract with wrong end date")
    public void i_try_to_update_the_contract_with_wrong_end_date() {
        c = (ContractDto) ctx.get(Key.INFORCE);
        tryUpdateAndKeepException(c);
    }

    @When("I update the end date of the contract to")
    public void i_update_the_end_date_of_the_contract(DataTable t)
            throws BusinessException {
        LocalDate d =
                LocalDateConverter.convert(t.asMaps().get(0).get("newEndDate"));

        c = (ContractDto) ctx.get(Key.INFORCE);
        FindContractByIdSqlUnitOfWork unit =
                new FindContractByIdSqlUnitOfWork(c.id);
        unit.execute();
        this.before = unit.get().get();
        c.endDate = d;
        c.annualBaseWage = this.before.annualBaseWage;
        service.updateContract(c);
    }

    @Then("updatable fields are updated and all other fields remain the same")
    public void updatable_are_updated() {
        FindContractByIdSqlUnitOfWork unit =
                new FindContractByIdSqlUnitOfWork(c.id);
        unit.execute();
        this.after = unit.get().get();

        assertTrue(matchDates(c.endDate, this.after.endDate));
        assertEquals(this.after.version, c.version + 1);
        assertEquals(this.after.annualBaseWage, c.annualBaseWage, 0.0);
        notUpdatableFieldsRemainTheSame();
    }


    private boolean matchDates(LocalDate d1, LocalDate d2) {
        return (d1 == null && d2 == null) ||
                (d1 != null && d2 != null && d1.isEqual(d2));
    }

    private void notUpdatableFieldsRemainTheSame() {
        assertEquals(0, this.after.id.compareTo(this.before.id));
        assertEquals(0, this.after.contractTypeName.compareTo(
                this.before.contractTypeName));
        assertEquals(0, this.after.professionalGroupName.compareTo(
                this.before.professionalGroupName));
        assertEquals(0, this.after.state.compareTo(this.before.state));
        assertTrue(this.after.startDate.isEqual(this.before.startDate));
        assertEquals(0, this.after.dni.compareTo(this.before.dni));
        assertEquals(this.after.settlement, this.before.settlement, 0.0);
    }

    @When("I update wage of the contract to")
    public void i_update_wage_of_the_contract(DataTable t)
            throws BusinessException {
        double w = Double.parseDouble(t.asMaps().get(0).get("wage"));

        c = (ContractDto) ctx.get(Key.INFORCE);
        FindContractByIdSqlUnitOfWork unit =
                new FindContractByIdSqlUnitOfWork(c.id);
        unit.execute();
        this.before = unit.get().get();
        c.annualBaseWage = w;
        service.updateContract(c);
    }

    @When("I update end date and wage of the contract to")
    public void i_update_end_date_and_wage_of_the_contract(DataTable t)
            throws BusinessException {
        double w = Double.parseDouble(t.asMaps().get(0).get("newWage"));
        LocalDate d =
                LocalDateConverter.convert(t.asMaps().get(0).get("newEndDate"));

        c = (ContractDto) ctx.get(Key.INFORCE);
        FindContractByIdSqlUnitOfWork unit =
                new FindContractByIdSqlUnitOfWork(c.id);
        unit.execute();
        this.before = unit.get().get();
        c.endDate = d;
        c.annualBaseWage = w;
        service.updateContract(c);
    }

    private void tryUpdateAndKeepException(ContractDto c) {
        try {
            service.updateContract(c);
            fail();
        } catch (BusinessException ex) {
            ctx.setException(ex);
        } catch (IllegalArgumentException ex) {
            ctx.setException(ex);
        }
    }


}