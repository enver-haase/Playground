/**
 *
 */
package org.vaadin.enver.ui.views.orderedit;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.Encode;
import com.vaadin.flow.templatemodel.Include;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.vaadin.enver.backend.data.entity.Order;
import org.vaadin.enver.ui.events.CancelEvent;
import org.vaadin.enver.ui.events.SaveEvent;
import org.vaadin.enver.ui.utils.converters.CurrencyFormatter;
import org.vaadin.enver.ui.utils.converters.LocalDateTimeConverter;
import org.vaadin.enver.ui.utils.converters.LocalTimeConverter;
import org.vaadin.enver.ui.utils.converters.LongToStringConverter;
import org.vaadin.enver.ui.utils.converters.OrderStateConverter;
import org.vaadin.enver.ui.views.storefront.converters.StorefrontLocalDateConverter;
import org.vaadin.enver.ui.views.storefront.events.CommentEvent;
import org.vaadin.enver.ui.views.storefront.events.EditEvent;

/**
 * The component displaying a full (read-only) summary of an order, and a comment
 * field to add comments.
 */
@Tag("order-details")
@JsModule("./src/views/orderedit/order-details.js")
public class OrderDetails extends PolymerTemplate<OrderDetails.Model> {

	private Order order;

	@Id("back")
	private Button back;

	@Id("cancel")
	private Button cancel;

	@Id("save")
	private Button save;

	@Id("edit")
	private Button edit;

	@Id("history")
	private Element history;

	@Id("comment")
	private Element comment;

	@Id("sendComment")
	private Button sendComment;

	@Id("commentField")
	private TextField commentField;

	private boolean isDirty;

	public OrderDetails() {
		sendComment.addClickListener(e -> {
			String message = commentField.getValue();
			message = message == null ? "" : message.trim();
			if (!message.isEmpty()) {
				commentField.clear();
				fireEvent(new CommentEvent(this, order.getId(), message));
			}
		});
		save.addClickListener(e -> fireEvent(new SaveEvent(this, false)));
		cancel.addClickListener(e -> fireEvent(new CancelEvent(this, false)));
		edit.addClickListener(e -> fireEvent(new EditEvent(this)));
	}

	public void display(Order order, boolean review) {
		getModel().setReview(review);
		this.order = order;
		getModel().setItem(order);
		if (!review) {
			commentField.clear();
		}
		this.isDirty = review;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	public interface Model extends TemplateModel {
		@Include({ "id", "dueDate.day", "dueDate.weekday", "dueDate.date", "dueTime", "state", "pickupLocation.name",
			"customer.fullName", "customer.phoneNumber", "customer.details", "items.product.name", "items.comment",
			"items.quantity", "items.product.price", "history.message", "history.createdBy.firstName",
			"history.timestamp", "history.newState", "totalPrice" })
		@Encode(value = LongToStringConverter.class, path = "id")
		@Encode(value = StorefrontLocalDateConverter.class, path = "dueDate")
		@Encode(value = LocalTimeConverter.class, path = "dueTime")
		@Encode(value = OrderStateConverter.class, path = "state")
		@Encode(value = CurrencyFormatter.class, path = "items.product.price")
		@Encode(value = LocalDateTimeConverter.class, path = "history.timestamp")
		@Encode(value = OrderStateConverter.class, path = "history.newState")
		@Encode(value = CurrencyFormatter.class, path = "totalPrice")
		void setItem(Order order);

		void setReview(boolean review);
	}

	public Registration addSaveListenter(ComponentEventListener<SaveEvent> listener) {
		return addListener(SaveEvent.class, listener);
	}

	public Registration addEditListener(ComponentEventListener<EditEvent> listener) {
		return addListener(EditEvent.class, listener);
	}

	public Registration addBackListener(ComponentEventListener<ClickEvent<Button>> listener) {
		return back.addClickListener(listener);
	}

	public Registration addCommentListener(ComponentEventListener<CommentEvent> listener) {
		return addListener(CommentEvent.class, listener);
	}

	public Registration addCancelListener(ComponentEventListener<CancelEvent> listener) {
		return addListener(CancelEvent.class, listener);
	}
}
