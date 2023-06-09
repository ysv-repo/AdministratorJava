package user.dynamicTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicTableServiceImpl implements DynamicTableService {

	@Autowired
	TableRepo tableRepo;

	@Autowired
	TableColumnRepo colRepo;

	@Autowired
	TableDataRepo dataRepo;

	@Override
	public void SaveTableDetails(TableDetails details) {
		tableRepo.save(details);
	}

	@Override
	public TableDetails getTableDetailsById(Integer id) {
		return tableRepo.findById(id).get();
	}

	@Override
	public void createColumnDetails(List<TableColumnDetails> details) {

		for (TableColumnDetails col : details) {
			colRepo.save(col);
		}

	}

	@Override
	public List<TableColumnDetails> getColumnDetails(Integer id) {
		List<Object[]> colsList = colRepo.getColumnDetails(id);

		List<TableColumnDetails> details = new ArrayList<>();

		for (Object[] col : colsList) {
			TableColumnDetails tableColumnDetails = new TableColumnDetails(
					new TableColumnsId((Integer) col[0], (Integer) col[1]), (String) col[2], (String) col[3],
					(Integer) col[4], (Integer) col[5], (String) col[6], col[7].equals(0) ? false : true);

			details.add(tableColumnDetails);
		}

		return details;
	}

	@Override
	public List<TableDetails> getAllTables() {
		return tableRepo.findAll();
	}

	@Override
	public String getTableData(Integer tableId) {
		return dataRepo.findTableDataById(tableId);
	}

	@Override
	public Integer getMaxTableNo() {
		return tableRepo.getMaxTableNo() == null ? 0 : tableRepo.getMaxTableNo();
	}

	@Override
	public void addDefaultRow(TableData tableData) {
		dataRepo.save(tableData);
	}

	@Override
	public void saveOrUpdateTableData(int id, String json_Data) {
		dataRepo.save(new TableData(new TableDataId(id), json_Data));		
	}

}
