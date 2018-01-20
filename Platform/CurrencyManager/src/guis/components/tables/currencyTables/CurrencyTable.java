package guis.components.tables.currencyTables;

import java.util.ArrayList;
import java.util.Collection;

import arithmetic.Pfloat;
import currencies.Currency;
import currencies.CurrencyFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.guis.ThreadingUtils;

public abstract class CurrencyTable<R extends CurrencyData> extends TableView<R> {
	public CurrencyTable() {
		initializeColumns();
	}

	protected void initializeColumns() {
		ObservableList<TableColumn<R, ?>> columns = this.getColumns();

		final TableColumn<R, Integer> rankColumn = new TableColumn<>("Rank");
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
		rankColumn.setStyle("-fx-alignment: CENTER;");
		rankColumn.setMinWidth(65);
		rankColumn.setMaxWidth(75);
		columns.add(rankColumn);

		final TableColumn<R, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setStyle("-fx-alignment: CENTER;");
		nameColumn.setMinWidth(75);
		columns.add(nameColumn);

		final TableColumn<R, Pfloat> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		priceColumn.setMinWidth(75);
		columns.add(priceColumn);

		final TableColumn<R, Pfloat> marketCapColumn = new TableColumn<>("Market Cap");
		marketCapColumn.setCellValueFactory(new PropertyValueFactory<>("marketCap"));
		marketCapColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		marketCapColumn.setMinWidth(115);
		columns.add(marketCapColumn);

		final TableColumn<R, Pfloat> volumeColumn = new TableColumn<>("Volume");
		volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
		volumeColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		volumeColumn.setMinWidth(85);
		columns.add(volumeColumn);
	}

	public void refresh() {
		ThreadingUtils.run(this::loadRowsAsync, this::loadRowsSync);
	}

	private Collection<R> loadRowsAsync() {
		Collection<R> newRows = new ArrayList<>();
		for (Currency currency : CurrencyFactory.getAllCurrencies()) {
			R newRow = loadRow(currency);
			if (newRow != null) {
				newRows.add(newRow);
			}
		}
		return newRows;
	}

	protected void loadRowsSync(Collection<R> newRows) {
		ObservableList<R> rows = this.getItems();
		rows.clear();
		rows.addAll(newRows);
	}

	protected abstract R loadRow(Currency currency);
}
