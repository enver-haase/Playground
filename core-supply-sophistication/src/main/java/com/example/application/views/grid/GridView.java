package com.example.application.views.grid;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.example.application.data.entity.Client;
import com.example.application.service.ClientService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.function.SerializablePredicate;
import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@CssImport("./views/grid/grid-view.css")
@Route(value = "grid", layout = MainView.class)
@PageTitle("Grid")
public class GridView extends Div {

    public class MyStatefulFilter implements SerializablePredicate<Client> {
        private String idFilter;
        private String clientNameFilter;
        private String amountFilter;
        private String statusFIlter;

        @Override
        public boolean test(Client client) {
            normalize();
            return StringUtils.containsIgnoreCase(Integer.toString(client.getId()), idFilter) &&
                    StringUtils.containsIgnoreCase(client.getClient(), clientNameFilter) &&
                    StringUtils.containsIgnoreCase(Double.toString(client.getAmount()), amountFilter) &&
                    StringUtils.containsIgnoreCase(client.getStatus(), statusFIlter);
        }

        /**
         * Hackish method to allow the back-end to check if this filter has any effect at all.
         * Could also be achieved by setting the filter to null instead of keeping a singleton instance set at all times.
         * @return
         */
        public boolean isEffective() {
            normalize();
            return !idFilter.isEmpty() || !clientNameFilter.isEmpty() || !amountFilter.isEmpty() || !statusFIlter.isEmpty();
        }

        private void normalize() {
            if (idFilter == null) {
                idFilter = StringUtils.EMPTY;
            }
            if (clientNameFilter == null) {
                clientNameFilter = StringUtils.EMPTY;
            }
            if (amountFilter == null) {
                amountFilter = StringUtils.EMPTY;
            }
            if (statusFIlter == null) {
                statusFIlter = StringUtils.EMPTY;
            }
        }
    }

    final MyStatefulFilter myStatefulFilter = new MyStatefulFilter();

    private GridPro<Client> grid;

    private Grid.Column<Client> idColumn;
    private Grid.Column<Client> clientColumn;
    private Grid.Column<Client> amountColumn;
    private Grid.Column<Client> statusColumn;

    private final ClientService clientService;

    public GridView(ClientService clientService) {
        this.clientService = clientService;

        addClassName("grid-view");
        setSizeFull();
        createGrid();
        add(grid);
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
        addFiltersToGrid();
    }

    private void createGridComponent() {
        grid = new GridPro<>();
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setHeight("100%");

        //dataProvider = new ListDataProvider<>(getClients());
        ConfigurableFilterDataProvider<Client, Void, SerializablePredicate<Client>> dataProvider = DataProvider.fromFilteringCallbacks(clientService::fetch, clientService::size).withConfigurableFilter();
        dataProvider.setFilter(this.myStatefulFilter);
        grid.setDataProvider(dataProvider);
    }


    private void addColumnsToGrid() {
        createIdColumn();
        createClientColumn();
        createAmountColumn();
        createStatusColumn();
        createStatus2Column();
        createStatus3Column();
        createDateColumn();
    }

    private void createIdColumn() {
        idColumn = grid.addColumn(Client::getId, "id").setHeader("ID").setWidth("120px").setFlexGrow(0);
    }

    private void createClientColumn() {
        clientColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(Alignment.CENTER);
            Image img = new Image(client.getImg(), "");
            Span span = new Span();
            span.setClassName("name");
            span.setText(client.getClient());
            hl.add(img, span);
            return hl;
        }))/*.setComparator(client -> client.getClient()) */.setHeader("Client");
    }

    private void createAmountColumn() {
        amountColumn = grid
                .addEditColumn(Client::getAmount,
                        new NumberRenderer<>(client -> client.getAmount(), NumberFormat.getCurrencyInstance(Locale.US)))
                .text((item, newValue) -> item.setAmount(Double.parseDouble(newValue)))
                /*.setComparator(client -> client.getAmount())*/.setHeader("Amount");
    }

    private void createStatusColumn() {
        statusColumn = grid.addEditColumn(Client::getClient, new ComponentRenderer<>(client -> {
            Span span = new Span();
            span.setText(client.getStatus());
            span.getElement().setAttribute("theme", "badge " + client.getStatus().toLowerCase());
            return span;
        })).select((item, newValue) -> item.setStatus(newValue), Arrays.asList("Pending", "Success", "Error"))
                /*.setComparator(client -> client.getStatus())*/.setHeader("Status as a Component");
    }

    private void createStatus2Column() {
        grid.addColumn(TemplateRenderer.<Client>of("<b on-click='myclicked'>[[item.mystatus]]</b>")
                .withProperty("mystatus", Client::getStatus)
                .withEventHandler("myclicked", item -> Notification.show(item.getStatus())))
                .setHeader("Status in HTML");
    }

    private void createStatus3Column() {
        grid.addColumn(TemplateRenderer.<Client>of(
                "<template is=\"dom-if\" if=\"[[item.isValidConfig]]\">" +
                        "<span style=\"background-color: green\">VALID</span>" +
                        "</template>" +
                        "<template is=\"dom-if\" if=\"[[!item.isValidConfig]]\">" +
                        "<span style=\"background-color: red\">INVALID</span>" +
                        "</template>")
                .withProperty("isValidConfig", item -> !item.getStatus().equals("Error")))
                .setHeader("Conditional Status in HTML");
    }

    private void createDateColumn() {
        grid
                .addColumn(new LocalDateRenderer<>(client -> LocalDate.parse(client.getDate()),
                        DateTimeFormatter.ofPattern("M/d/yyyy")))
                /*.setComparator(Client::getDate)*/.setHeader("Date").setWidth("180px").setFlexGrow(0);
    }

    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField idFilter = new TextField();
        idFilter.setPlaceholder("Filter");
        idFilter.setClearButtonVisible(true);
        idFilter.setWidth("100%");
        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        //idFilter.addValueChangeListener(event -> dataProvider.addFilter(
        //        client -> StringUtils.containsIgnoreCase(Integer.toString(client.getId()), idFilter.getValue())));
        idFilter.addValueChangeListener(event -> {
            myStatefulFilter.idFilter = idFilter.getValue();
            grid.getDataProvider().refreshAll();
        });
        filterRow.getCell(idColumn).setComponent(idFilter);

        TextField clientFilter = new TextField();
        clientFilter.setPlaceholder("Filter");
        clientFilter.setClearButtonVisible(true);
        clientFilter.setWidth("100%");
        clientFilter.setValueChangeMode(ValueChangeMode.EAGER);
        //clientFilter.addValueChangeListener(event -> dataProvider
        //        .addFilter(client -> StringUtils.containsIgnoreCase(client.getClient(), clientFilter.getValue())));
        clientFilter.addValueChangeListener(event -> {
            myStatefulFilter.clientNameFilter = clientFilter.getValue();
            grid.getDataProvider().refreshAll();
        });
        filterRow.getCell(clientColumn).setComponent(clientFilter);

        TextField amountFilter = new TextField();
        amountFilter.setPlaceholder("Filter");
        amountFilter.setClearButtonVisible(true);
        amountFilter.setWidth("100%");
        amountFilter.setValueChangeMode(ValueChangeMode.EAGER);
        //amountFilter.addValueChangeListener(event -> dataProvider.addFilter(client -> StringUtils
        //        .containsIgnoreCase(Double.toString(client.getAmount()), amountFilter.getValue())));
        amountFilter.addValueChangeListener(event -> {
            myStatefulFilter.amountFilter = amountFilter.getValue();
            grid.getDataProvider().refreshAll();
        });
        filterRow.getCell(amountColumn).setComponent(amountFilter);

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems(Arrays.asList("Pending", "Success", "Error"));
        statusFilter.setPlaceholder("Filter");
        statusFilter.setClearButtonVisible(true);
        statusFilter.setWidth("100%");
        //statusFilter.addValueChangeListener(
        //        event -> dataProvider.addFilter(client -> areStatusesEqual(client, statusFilter)));
        statusFilter.addValueChangeListener(event -> {
            myStatefulFilter.statusFIlter = statusFilter.getValue();
            grid.getDataProvider().refreshAll();
        });
        filterRow.getCell(statusColumn).setComponent(statusFilter);

    }

//    private List<Client> getClients() {
//        return Arrays.asList(
//                createClient(4957, "https://randomuser.me/api/portraits/women/42.jpg", "Amarachi Nkechi", 47427.0,
//                        "Success", "2019-05-09"),
//                createClient(675, "https://randomuser.me/api/portraits/women/24.jpg", "Bonelwa Ngqawana", 70503.0,
//                        "Success", "2019-05-09"),
//                createClient(6816, "https://randomuser.me/api/portraits/men/42.jpg", "Debashis Bhuiyan", 58931.0,
//                        "Success", "2019-05-07"),
//                createClient(5144, "https://randomuser.me/api/portraits/women/76.jpg", "Jacqueline Asong", 25053.0,
//                        "Pending", "2019-04-25"),
//                createClient(9800, "https://randomuser.me/api/portraits/men/24.jpg", "Kobus van de Vegte", 7319.0,
//                        "Pending", "2019-04-22"),
//                createClient(3599, "https://randomuser.me/api/portraits/women/94.jpg", "Mattie Blooman", 18441.0,
//                        "Error", "2019-04-17"),
//                createClient(3989, "https://randomuser.me/api/portraits/men/76.jpg", "Oea Romana", 33376.0, "Pending",
//                        "2019-04-17"),
//                createClient(1077, "https://randomuser.me/api/portraits/men/94.jpg", "Stephanus Huggins", 75774.0,
//                        "Success", "2019-02-26"),
//                createClient(8942, "https://randomuser.me/api/portraits/men/16.jpg", "Torsten Paulsson", 82531.0,
//                        "Pending", "2019-02-21"));
//    }
//
//    private Client createClient(int id) {
//        Client c = new Client();
//        c.setId(id);
//        c.setImg(img);
//        c.setClient(client);
//        c.setAmount(amount);
//        c.setStatus(status);
//        c.setDate(date);
//
//        return c;
//    }
};
