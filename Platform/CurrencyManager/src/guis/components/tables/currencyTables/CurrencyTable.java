package guis.components.tables.currencyTables;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import arithmetic.Pfloat;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import platforms.currencies.Currency;
import platforms.currencies.CurrencyFactory;
import utils.collections.iterables.IterableUtils;
import utils.guis.ThreadingUtils;

public abstract class CurrencyTable<R extends CurrencyData> extends TableView<R> {
	public CurrencyTable() {
		this(null);
	}

	public CurrencyTable(Duration autoRefreshRate) {
		initializeColumns();

		if (autoRefreshRate != null) {
			ThreadingUtils.runForever(this::refresh, autoRefreshRate);
		}

		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	protected void initializeColumns() {
		List<TableColumn<R, ?>> columns = this.getColumns();

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

	@Override
	public void refresh() {
		super.refresh();
		ThreadingUtils.run(this::loadRowsAsync, this::loadRowsSync);
	}

	private Collection<R> loadRowsAsync() {
		Collection<R> newRows = new ArrayList<>();

		Iterable<R> iter = IterableUtils.map(CurrencyFactory.getAllCurrencies(), this::loadRow);
		iter = IterableUtils.filter(iter, row -> row != null);
		iter.forEach(newRows::add);

		return newRows;
	}

	private void loadRowsSync(Collection<R> newRows) {
		ObservableList<R> rows = this.getItems();
		rows.clear();
		rows.addAll(newRows);
		super.refresh();
	}

	protected abstract R loadRow(Currency currency);
}
