package applications.components.tables.currencyTables;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import arithmetic.Pfloat;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import platforms.currencies.Currency;
import platforms.currencies.CurrencyFactory;
import utils.collections.iterables.IterableUtils;
import utils.guis.ThreadingUtils;

public abstract class CurrencyTable<R extends CurrencyData> extends TableView<R> {
	private Node normalPlaceholder;
	private Node errorPlaceholder;

	public CurrencyTable() {
		this(null);
	}

	public CurrencyTable(Duration autoRefreshRate) {
		initializeColumns();

		if (autoRefreshRate != null) {
			ThreadingUtils.runForever(this::refreshRows, autoRefreshRate);
		}

		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		normalPlaceholder = makePlaceHolder("Loading Currencies");
		errorPlaceholder = makePlaceHolder("Error Loading Currencies... Retrying");

		this.setPlaceholder(normalPlaceholder);
	}

	private Node makePlaceHolder(String msg) {
		Label label = new Label(msg);
		ProgressIndicator pi = new ProgressIndicator();
		HBox hb = new HBox(label, pi);
		hb.setAlignment(Pos.CENTER);
		hb.spacingProperty().set(20);
		return hb;
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

	public void refreshRows() {
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

		if (newRows != null && !newRows.isEmpty()) {
			rows.addAll(newRows);
			this.setPlaceholder(normalPlaceholder);
		} else {
			this.setPlaceholder(errorPlaceholder);
		}
		super.refresh();
	}

	protected abstract R loadRow(Currency currency);
}
