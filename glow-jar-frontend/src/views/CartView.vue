<template>
  <v-container class="py-10">
    <div class="cart-shell">
      <h1 class="cart-title mb-6">Coșul tău</h1>

      <v-card v-if="!cartStore.items.length" class="pa-8 text-center" rounded="xl" elevation="0">
        <p class="mb-4">Coșul este gol momentan.</p>
        <v-btn class="gold-btn" to="/">Continuă shopping-ul</v-btn>
      </v-card>

      <div v-else class="cart-grid">
        <v-card class="pa-6" rounded="xl" elevation="0">
          <div
            v-for="item in cartStore.items"
            :key="item.id"
            class="cart-item"
          >
            <div class="cart-main">
              <h3>{{ item.name }}</h3>
              <p class="item-type">{{ item.type === 'custom-jar' ? 'Jar personalizat' : 'Produs' }}</p>

              <div v-if="item.customization" class="custom-details">
                <p><strong>Stil:</strong> {{ item.customization.style || '—' }}</p>
                <p><strong>Material:</strong> {{ item.customization.material || '—' }}</p>
                <p><strong>Premium:</strong> {{ item.customization.premiumOnly ? 'Da' : 'Nu' }}</p>
                <p><strong>Price range:</strong> {{ item.customization.priceRange || '—' }}</p>
                <p><strong>Mărime:</strong> {{ item.customization.size || '—' }}</p>
                <p><strong>Note:</strong> {{ item.customization.notes || '—' }}</p>
              </div>
            </div>

            <div class="cart-side">
              <p class="price">{{ item.price }} RON</p>

              <div class="qty-row">
                <v-btn size="x-small" icon="mdi-minus" @click="cartStore.updateQuantity(item.id, item.quantity - 1)" />
                <span>{{ item.quantity }}</span>
                <v-btn size="x-small" icon="mdi-plus" @click="cartStore.updateQuantity(item.id, item.quantity + 1)" />
              </div>

              <v-btn variant="text" color="error" @click="cartStore.removeItem(item.id)">
                Șterge
              </v-btn>
            </div>
          </div>
        </v-card>

        <v-card class="pa-6" rounded="xl" elevation="0">
          <h2 class="mb-4">Sumar comandă</h2>
          <div class="summary-line">
            <span>Produse</span>
            <strong>{{ cartStore.itemCount }}</strong>
          </div>
          <div class="summary-line">
            <span>Subtotal</span>
            <strong>{{ cartStore.subtotal }} RON</strong>
          </div>

          <v-btn class="gold-btn mt-6" block size="large">
            Mergi la checkout
          </v-btn>
        </v-card>
      </div>
    </div>
  </v-container>
</template>

<script setup lang="ts">
import { useCartStore } from '@/stores/cart'

const cartStore = useCartStore()
</script>

<style scoped>
.cart-shell {
  max-width: 1200px;
  margin: 0 auto;
}

.cart-title {
  font-size: 40px;
}

.cart-grid {
  display: grid;
  grid-template-columns: 1.3fr 0.8fr;
  gap: 24px;
}

.cart-item {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  padding: 18px 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.08);
}

.item-type {
  color: #836f78;
  margin-bottom: 12px;
}

.custom-details p {
  margin-bottom: 6px;
  color: #54484d;
}

.cart-side {
  min-width: 120px;
  text-align: right;
}

.price {
  font-weight: 700;
  margin-bottom: 12px;
}

.qty-row {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 10px;
  margin-bottom: 8px;
}

.summary-line {
  display: flex;
  justify-content: space-between;
  margin-bottom: 14px;
}

.gold-btn {
  background: #d5ae58;
  color: white;
  border-radius: 999px;
  text-transform: none;
  font-weight: 600;
}

@media (max-width: 960px) {
  .cart-grid {
    grid-template-columns: 1fr;
  }

  .cart-item {
    flex-direction: column;
  }

  .cart-side {
    text-align: left;
  }

  .qty-row {
    justify-content: flex-start;
  }
}
</style>