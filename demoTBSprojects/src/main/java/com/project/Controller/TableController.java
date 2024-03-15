package com.project.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.Model.Cuisine;
import com.project.Model.Table;
import com.project.Service.TableService;

//
//@RestController
//@RequestMapping("/api/tables")
//public class TableController {
//	
//	
//	@Autowired
//    private TableService tableService;
//	
//	@GetMapping
//	public List<Table> getAllTables() {
//		return tableService.getAllTables();
//	}
//	
//	@GetMapping("/available")
//	public List<Table> getAvailableTables() {
//		return tableService.getAvailableTables();
//	}
//	
//	@GetMapping("/{tableId}")
//    public Table getTableById(@PathVariable Long tableId) {
//        return tableService.getTableByTableId(tableId);
//    }
//	
//	@PostMapping("/add")
//    public String addTable(@RequestBody Table table ) {
//        tableService.addTable(table);
//        return "Table added successfully";
//    }
//	
//	@PostMapping("/{tableId}/set-available")
//    public ResponseEntity<?> setTableAsAvailable(@PathVariable Long tableId) {
//        return new ResponseEntity<>(tableService.setTableAsAvailable(tableId),HttpStatus.OK);
//    	
//    }
//}


@Controller
public class TableController {

    @Autowired
    private TableService tableService;
    
    @PostMapping("/prebook/{tableId}")
    public String preBookTable(@PathVariable Long tableId) {
        Table table = tableService.getTableByTableId( tableId);
        if (table != null) {
            table.setBooked(true);
            tableService.save(table);
        }
        return "redirect:/tables";
    }


    @GetMapping("/tables")
    public String getAllTables(Model model) {
        List<Table> tables = tableService.getAllTables();
        model.addAttribute("tables", tables);
        return "tableList"; // HTML view name for listing tables
    }

    @GetMapping("/tables/available")
    public String getAvailableTables(Model model) {
        List<Table> availableTables = tableService.getAvailableTables();
        model.addAttribute("availableTables", availableTables);
        return "availableTableList"; // HTML view name for listing available tables
    }

    @GetMapping("/tables/{tableId}")
    public String getTableById(@PathVariable Long tableId, Model model) {
        Table table = tableService.getTableByTableId(tableId);
        model.addAttribute("table", table);
        return "tableDetails"; // HTML view name for displaying single table details
    }
    
//    @GetMapping("/tables/add")
//    public String showAddTableForm() {
//    	return "addTableForm";
//    }
    @GetMapping("/tables/add")
    public String addTableForm(Model model) {
        model.addAttribute("table", new Table());
        System.out.println("table....row");
        return "addTableForm"; // HTML view name for add cuisine form
    }

    @PostMapping("/tables/add")
    public String addTable(Table table) {
    	System.out.println("post methods");
    	table.setBooked(false);
        tableService.addTable(table);
        return "redirect:/tables"; // Redirect to the list of all tables
    }

    @PostMapping("/tables/setavailable/{tableId}")
    public String setTableAsAvailable(@PathVariable Long tableId) {
        tableService.setTableAsAvailable(tableId);
        Table table = tableService.getTableByTableId( tableId);
        if (table != null) {
            table.setBooked(false);
            tableService.save(table);
        }
        return "redirect:/tables"; // Redirect to the list of available tables
        }
}
