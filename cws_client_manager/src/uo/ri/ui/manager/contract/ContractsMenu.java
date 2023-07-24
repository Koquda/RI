package uo.ri.ui.manager.contract;

import uo.ri.ui.manager.contract.action.*;
import uo.ri.util.menu.BaseMenu;

public class ContractsMenu extends BaseMenu {

    public ContractsMenu() {
        menuOptions = new Object[][]{{"Manager > Contracts management", null},
                // Todo cambiar los action
                {"Add contract", AddContractAction.class},
                {"Update contract", UpdateContractAction.class},
                {"Delete contracts", DeleteContractAction.class},
                {"List all contracts", ListAllContractAction.class},
                {"List contract by id", ListContractByIdAction.class},
                {"List contracts by mechanic",
                        ListContractsByMechanicAction.class},
                {"Terminate contract", FinishContractAction.class},};
    }

}
