package com.stilevo.store.back.stilevo.project.api.domain.enums;

public enum OrderStatus {
    PENDING_PAYMENT,   // Pedido realizado, aguardando pagamento
    PAID,              // Pagamento confirmado
    PROCESSING,        // Pedido em preparação
    SHIPPED,           // Pedido enviado
    DELIVERED,         // Pedido entregue
    CANCELED,          // Pedido cancelado
    RETURNED;          // Pedido devolvido
}
