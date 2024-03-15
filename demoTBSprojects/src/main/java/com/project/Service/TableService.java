package com.project.Service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Model.Table;
import com.project.Repository.TableRepository;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    public Table addTable(Table table) {
        return tableRepository.save(table);
    }

    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    public Table getTableByTableId(Long tableId) {
        return tableRepository.findBytableId(tableId).orElse(null);
    }

    public List<Table> getAvailableTables() {
        return tableRepository.findByBookedFalse();
    }
    

    public String setTableAsAvailable(Long tableId) {
        Optional<Table> optionalTable = tableRepository.findBytableId(tableId);
        if (optionalTable.isPresent()) {
            Table table = optionalTable.get();
            if (table.isBooked()) {
                table.setBooked(false);
                tableRepository.save(table);
                return "Table set as available successfully";
            } else {
                return "Table is not yet booked";
            }
        }
        return "Table not found";
    }

	public void save(Table table) {
		// TODO Auto-generated method stub
		tableRepository.save(table);
	}
}
