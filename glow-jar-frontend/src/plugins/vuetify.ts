import "vuetify/styles"
import { createVuetify } from "vuetify"
import * as components from "vuetify/components"
import * as directives from "vuetify/directives"
import { aliases, mdi } from "vuetify/iconsets/mdi"
import "@mdi/font/css/materialdesignicons.css"

export default createVuetify({
  components,
  directives,
  theme: {
    defaultTheme: "boutique",
    themes: {
      boutique: {
        dark: false,
        colors: {
          background: "#FFF8F2",
          surface: "#FFFFFF",
          primary: "#EADCF8",
          secondary: "#F8E8EE",
          accent: "#C6A75E",
          info: "#6B7AA1",
          success: "#2E7D32",
          warning: "#FB8C00",
          error: "#B00020",
        },
      },
    },
  },
  icons: {
    defaultSet: "mdi",
    aliases,
    sets: { mdi },
  },
})