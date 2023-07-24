package uo.ri.cws.application.service.util;

import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.cws.application.service.contract.ContractService.ContractSummaryDto;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.service.util.sql.*;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.util.math.Round;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class ContractUtil {

    private final double minAnnualWage = 965.0;
    private final double maxAnnualWage = 5000.0;
    private ContractDto dto = null;
    private MechanicDto mDto;
    private ContractTypeDto ctDto;
    private ProfessionalGroupBLDto pgDto;
    private List<ContractDto> list = new ArrayList<>();

    public static boolean match(ContractDto c1, ContractDto c2) {
        if (c1 == c2) return true;
        if (c1 == null) return (c2 == null);

        return c1.state.equals(c2.state) && match(c1.id, c2.id) &&
                match(c1.dni, c2.dni) &&
                match(c1.professionalGroupName, c2.professionalGroupName) &&
                match(c1.contractTypeName, c2.contractTypeName) &&
                Math.abs(c1.annualBaseWage - c2.annualBaseWage) <
                        Double.MIN_NORMAL &&
                Math.abs(c1.settlement - c2.settlement) < Double.MIN_NORMAL &&
                matchDates(c1.startDate, c2.startDate) &&
                matchDates(c1.endDate, c2.endDate);
    }

    public static boolean matchDates(LocalDate d1, LocalDate d2) {
        return (d1 == null && d2 == null) ||
                (d1 != null && d2 != null && d1.isEqual(d2));
    }

    private static boolean match(String id1, String id2) {
        return (id1.compareTo(id2) == 0);
    }

    public static boolean match(ContractSummaryDto cs, ContractDto c) {
        // null check
        if (cs == null) return (c == null);

        return cs.state.equals(c.state) && (match(cs.id, c.id)) &&
                (match(cs.dni, c.dni)) &&
                Math.abs(cs.settlement - c.settlement) < Double.MIN_NORMAL;
    }

    public ContractUtil unique() {
        dto = new ContractDto();
        dto.id = UUID.randomUUID().toString();
        dto.version = 1L;
        dto.startDate =
                LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        double value =
                new Random().nextDouble() * (maxAnnualWage - minAnnualWage) +
                        minAnnualWage;
        dto.annualBaseWage = Round.twoCents(value);
        dto.contractTypeName = "PERMANENT";
        dto.professionalGroupName = "I";
        dto.settlement = 0.0;
        dto.state = ContractState.IN_FORCE;
        return this;
    }

    public ContractUtil register() {
        String contractTypeId = this.ctDto.id;
        String professionalGroupId = this.pgDto.id;
        String mechanicId = this.mDto.id;
        if (this.dto.state.equals(ContractState.IN_FORCE)) {
            new AddContractSqlUnitOfWork(dto, mechanicId, contractTypeId,
                    professionalGroupId).execute();
        } else {
            new AddContractTerminatedSqlUnitOfWork(dto, mechanicId,
                    contractTypeId, professionalGroupId).execute();
        }
        return this;
    }

    public ContractUtil registerIfNew() {
        FindContractByIdSqlUnitOfWork unit =
                new FindContractByIdSqlUnitOfWork(dto.id);
        unit.execute();
        Optional<ContractDto> op = unit.get();
        if (op.isEmpty()) {
            register();
        } else {
            dto.id = op.get().id;
        }
        return this;
    }

//
//	public ContractUtil forMechanics(MechanicBLDto m) {
//		this.mDto = m;
//		this.dto.dni = m.dni;
//		return this;
//	}

    public ContractDto get() {
        return this.dto;
    }

    public List<ContractDto> getList() {
        return this.list;
    }

    public ContractUtil withId(String id) {
        this.dto.id = id;
        return this;
    }

    public ContractUtil forMechanic(MechanicDto m) {
        this.mDto = m;
        this.dto.dni = m.dni;
        return this;
    }

    public ContractUtil withType(ContractTypeDto ct) {
        this.ctDto = ct;
        this.dto.contractTypeName = ct.name;
        return this;
    }

    public ContractUtil withGroup(ProfessionalGroupBLDto pg) {
        this.pgDto = pg;
        this.dto.professionalGroupName = pg.name;
        return this;
    }

    public ContractUtil withState(String string) {
        this.dto.state = ContractState.valueOf(string);
        return this;
    }

    public ContractUtil withStartDate(LocalDate arg) {
        this.dto.startDate = arg;
        return this;
    }

    public ContractUtil withEndDate(LocalDate arg) {
        this.dto.endDate = arg;
        return this;
    }

    public ContractUtil withAnnualWage(Double arg) {
        this.dto.annualBaseWage = arg;
        return this;
    }

    public ContractUtil findContractById(String id) {
        FindContractByIdSqlUnitOfWork unit =
                new FindContractByIdSqlUnitOfWork(id);
        unit.execute();
        this.dto = unit.get().get();
        return this;
    }

    public ContractUtil findContractInForceForMechanic(String mechId) {
        FindContractInForceByMechanicIdSqlUnitOfWork unit =
                new FindContractInForceByMechanicIdSqlUnitOfWork(mechId);
        unit.execute();
        Optional<ContractDto> c = unit.get();
        if (c.isPresent()) {
            this.dto = c.get();
        } else {
            this.dto = null;
        }
        return this;

    }

    public ContractUtil findAllContractsInForce() {
        FindContractsInForceSqlUnitOfWork unit =
                new FindContractsInForceSqlUnitOfWork();
        unit.execute();
        list = unit.get();
        return this;

    }

}
