package uo.ri.cws.application.business;

import uo.ri.cws.application.business.client.ClientService;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.crud.MechanicCrudServiceImpl;

public class BusinessFactory {


	public static MechanicService forMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}

	public static InvoicingService forInvoicingService() {
		return new InvoicingServiceImpl();
	}
//
//	public static WorkOrderCrudService forWorkOrderService() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static CloseWorkOrderService forClosingBreakdown() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static VehicleCrudService forVehicleCrudService() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static CourseCrudService forCourseCrudService() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static CourseAttendanceService forCourseAttendanceService() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static CourseReportService forCourseReportService() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static CertificateService forCertificateService() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static VehicleTypeCrudService forVehicleTypeCrudService() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static AssignWorkOrderService forAssignWorkOrderService() {
//		return new AssignWorkOrderServiceImpl();
//	}
//
	public static ClientService forClientCrudService() {
		throw new RuntimeException("Not yet implemented");
	}
//
//	public static SparePartCrudService forSparePartCrudService() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static SettleInvoiceService forSettleInvoiceService() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static ClientHistoryService forClientHistoryService() {
//		throw new RuntimeException("Not yet implemented");
//	}
//
//	public static ViewAssignedWorkOrdersService forViewAssignedWorkOrdersService() {
//		throw new RuntimeException("Not yet implemented");
//	}

}

