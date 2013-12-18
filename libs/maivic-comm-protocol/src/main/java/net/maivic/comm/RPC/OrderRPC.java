package net.maivic.comm.RPC;

import java.util.List;

import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.protocol.Model.Decimal;
import net.maivic.protocol.Model.Order;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.protocol.Model.OrderEntryOption;
import net.maivic.protocol.Model.OrderStatus;

public interface OrderRPC {
	LazyResponse<OrderStatus> placeOrder(
			Order order, 
			List<OrderEntry> entry, 
			List<OrderEntryOption> entryOption,
			OrderStatus previousOrderStatus,
			@SubscriptionCallback Callback<OrderStatus> cb);
	LazyResponse<OrderStatus> confirmOrder(
			OrderStatus ord, 
			Decimal totalPrice , 
			@SubscriptionCallback Callback<OrderStatus> cb);
}
