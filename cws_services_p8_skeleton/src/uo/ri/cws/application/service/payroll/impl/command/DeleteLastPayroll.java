package uo.ri.cws.application.service.payroll.impl.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;

import java.time.LocalDate;
import java.util.List;

public class DeleteLastPayroll implements Command<Void> {

    @Override
    public Void execute() throws BusinessException {
        List<Payroll> payrollList = Factory.repository.forPayroll().findAll();
        LocalDate currentDate = LocalDate.now();

        for (Payroll payroll : payrollList) {
            if (isSameMonth(payroll.getDate(), currentDate)) {
                Factory.repository.forPayroll().remove(payroll);
            }
        }

        return null;
    }

    private boolean isSameMonth(LocalDate payrollDate, LocalDate currentDate) {
        int payrollYear = payrollDate.getYear();
        int currentYear = currentDate.getYear();
        int payrollMonth = payrollDate.getMonthValue();
        int currentMonth = currentDate.getMonthValue();

        return payrollYear == currentYear && payrollMonth == currentMonth;
    }

}
