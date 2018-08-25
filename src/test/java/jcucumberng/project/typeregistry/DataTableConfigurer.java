package jcucumberng.project.typeregistry;

import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import io.cucumber.datatable.TableTransformer;
import jcucumberng.project.domain.Transaction;

/*
 * Maps DataTables in feature files to custom domain objects.
 */
public class DataTableConfigurer implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry registry) {
		// DataTable with header row, multiple objects of Type<T>
		registry.defineDataTableType(new DataTableType(Transaction.class, new TableEntryTransformer<Transaction>() {
			@Override
			public Transaction transform(Map<String, String> entry) {
				return new Transaction(entry.get("name"), entry.get("amount"), entry.get("frequency"),
						entry.get("month"));
			}
		}));

		// DataTable with label column, single object of Type<T>
		registry.defineDataTableType(new DataTableType(Transaction.class, new TableTransformer<Transaction>() {
			@Override
			public Transaction transform(DataTable dataTable) throws Throwable {
				Map<String, String> map = dataTable.asMaps().get(0);
				return new Transaction(map);
			}
		}));
	}

}
