<template>
  <v-footer class="footer" color="transparent">
    <v-container class="footer-container">
      <v-row class="footer-top" align="center">
        <v-col cols="12" md="6">
          <div class="footer-title">{{ t("footer.subscribe") }}</div>

          <div class="email-box">
            <input type="email" :placeholder="t('footer.emailPlaceholder')" />
            <button type="button">→</button>
          </div>
        </v-col>

        <v-col cols="12" md="6" class="d-flex justify-md-end align-center">
          <v-btn class="shop-btn" rounded="xl">
            {{ t("footer.followShop") }}
          </v-btn>
        </v-col>
      </v-row>

      <v-divider class="footer-divider" />

      <v-row class="footer-middle" align="end">
        <v-col cols="12" sm="6" md="3">
          <div class="footer-small">{{ t("footer.country") }}</div>
          <select class="footer-select">
            <option>România | RON Lei</option>
          </select>
        </v-col>

        <v-col cols="12" sm="6" md="3">
          <div class="footer-small">{{ t("footer.language") }}</div>
          <select class="footer-select" :value="locale" @change="changeLanguage">
            <option
              v-for="lang in languages"
              :key="lang.value"
              :value="lang.value"
            >
              {{ lang.label }}
            </option>
          </select>
        </v-col>

        <v-col cols="12" md="6" class="d-flex justify-md-end align-center">
          <div class="payments">
            <img :src="visaImg" alt="Visa" />
            <img :src="mcImg" alt="Mastercard" />
            <img :src="klarnaImg" alt="Klarna" />
          </div>
        </v-col>
      </v-row>

      <div class="footer-bottom">
        {{ t("footer.copyright") }}
      </div>
    </v-container>
  </v-footer>
</template>

<script setup lang="ts">
import { useI18n } from "vue-i18n"
import visaImg from "../assets/payments/visa.png"
import mcImg from "../assets/payments/mc.png"
import klarnaImg from "../assets/payments/Klarna.png"

const { locale, t } = useI18n()

const languages = [
  { label: "Română", value: "ro" },
  { label: "English", value: "en" },
  { label: "Français", value: "fr" },
  { label: "Deutsch", value: "de" },
  { label: "Italiano", value: "it" },
  { label: "Español", value: "es" },
]

const changeLanguage = (event: Event) => {
  const target = event.target as HTMLSelectElement
  locale.value = target.value
  localStorage.setItem("locale", target.value)
}
</script>

<style scoped>
.footer {
  margin-top: 96px;
  padding-bottom: 40px;
  background: transparent !important;
}

.footer-container {
  border-top: 1px solid rgba(90, 59, 59, 0.12);
  padding: 40px 32px;
  background: white;
  border-radius: 24px;
  box-shadow: 0 8px 28px rgba(0, 0, 0, 0.06);
}

.footer-top {
  margin-bottom: 28px;
}

.footer-title {
  font-size: 18px;
  font-weight: 500;
  color: rgba(90, 59, 59, 0.95);
  margin-bottom: 16px;
}

.email-box {
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-width: 360px;
  height: 52px;
  border: 1px solid rgba(90, 59, 59, 0.22);
  background: rgba(255, 255, 255, 0.38);
  border-radius: 12px;
  overflow: hidden;
}

.email-box input {
  border: none;
  outline: none;
  width: 100%;
  height: 100%;
  padding: 0 16px;
  background: transparent;
  font-size: 15px;
  color: rgba(90, 59, 59, 0.9);
}

.email-box input::placeholder {
  color: rgba(90, 59, 59, 0.55);
}

.email-box button {
  border: none;
  background: transparent;
  width: 54px;
  height: 100%;
  cursor: pointer;
  font-size: 22px;
  color: rgba(90, 59, 59, 0.75);
  transition: background-color 0.2s ease, color 0.2s ease;
}

.email-box button:hover {
  background: rgba(90, 59, 59, 0.05);
  color: rgba(90, 59, 59, 0.95);
}

.shop-btn {
  background: #6b4df5 !important;
  color: white !important;
  text-transform: none !important;
  font-weight: 700 !important;
  padding-inline: 20px !important;
  box-shadow: 0 10px 24px rgba(107, 77, 245, 0.22);
}

.footer-divider {
  margin-bottom: 28px;
  opacity: 0.7;
}

.footer-middle {
  margin-bottom: 20px;
}

.footer-small {
  font-size: 12px;
  color: rgba(90, 59, 59, 0.6);
  margin-bottom: 8px;
}

.footer-select {
  width: 100%;
  max-width: 220px;
  height: 44px;
  border: 1px solid rgba(90, 59, 59, 0.22);
  border-radius: 10px;
  padding: 0 12px;
  background: rgba(255, 255, 255, 0.38);
  color: rgba(90, 59, 59, 0.9);
  font-size: 14px;
  outline: none;
}

.payments {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 10px;
  flex-wrap: wrap;
}

.payments img {
  height: 28px;
  width: auto;
  display: block;
  object-fit: contain;
  padding: 4px 8px;
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.45);
  border: 1px solid rgba(90, 59, 59, 0.08);
}

.footer-bottom {
  margin-top: 18px;
  font-size: 12px;
  color: rgba(90, 59, 59, 0.58);
}

@media (max-width: 960px) {
  .footer {
    margin-top: 72px;
  }

  .footer-top {
    row-gap: 20px;
  }

  .payments {
    justify-content: flex-start;
    margin-top: 8px;
  }
}
</style>