import { defineStore } from 'pinia'

export interface JarCustomization {
  style: string
  material: string
  premiumOnly: boolean
  priceRange: string
  size: string
  notes: string
}

export interface CartItem {
  id: number
  type: 'custom-jar' | 'product'
  name: string
  image?: string
  price: number
  quantity: number
  customization?: JarCustomization
}

export const useCartStore = defineStore('cart', {
  state: () => ({
    items: [] as CartItem[]
  }),

  getters: {
    itemCount: (state) =>
      state.items.reduce((total, item) => total + item.quantity, 0),

    subtotal: (state) =>
      state.items.reduce((total, item) => total + item.price * item.quantity, 0)
  },

  actions: {
    addItem(item: CartItem) {
      const existing = this.items.find(
        (cartItem) =>
          cartItem.type === item.type &&
          cartItem.name === item.name &&
          JSON.stringify(cartItem.customization) === JSON.stringify(item.customization)
      )

      if (existing) {
        existing.quantity += item.quantity
      } else {
        this.items.push(item)
      }
    },

    removeItem(id: number) {
      this.items = this.items.filter((item) => item.id !== id)
    },

    clearCart() {
      this.items = []
    },

    updateQuantity(id: number, quantity: number) {
      const item = this.items.find((item) => item.id === id)
      if (!item) return

      if (quantity <= 0) {
        this.removeItem(id)
        return
      }

      item.quantity = quantity
    }
  }
})