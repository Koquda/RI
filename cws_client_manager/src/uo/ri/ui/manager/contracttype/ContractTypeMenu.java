package uo.ri.ui.manager.contracttype;

import uo.ri.ui.manager.contracttype.action.*;
import uo.ri.util.menu.BaseMenu;

public class ContractTypeMenu extends BaseMenu {

    public ContractTypeMenu() {
        menuOptions =
                new Object[][]{{"Manager > Contract Type management", null},

                        {"Add Contract Type", AddContractTypeAction.class},
                        {"Update Contract Type",
                                UpdateContractTypeAction.class},
                        {"Delete Contract Type",
                                DeleteContractTypeAction.class},
                        {"List Contract Types",
                                ListAllContractTypeAction.class},
                        {"List Contract Types by name",
                                ListContractTypeByNameAction.class},};
    }

}
