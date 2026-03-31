<template>
  <section class="auth-page">
    <v-container class="fill-height d-flex align-center justify-center">
      <v-card class="auth-card" elevation="0">
        <div class="auth-grid">
          <div class="auth-left">
            <p class="eyebrow">Verificare email</p>
            <h1 class="title">Confirmă contul tău</h1>
            <p class="subtitle">
              Introdu codul primit pe email pentru a activa contul și a continua experiența Glow Jar.
            </p>
          </div>

          <div class="auth-right">
            <v-form ref="formRef" @submit.prevent="handleVerify">
              <v-text-field
                v-model.trim="form.email"
                label="Email"
                type="email"
                variant="outlined"
                density="comfortable"
                class="field"
                :rules="emailRules"
              />

              <v-text-field
                v-model.trim="form.code"
                label="Cod de verificare"
                variant="outlined"
                density="comfortable"
                class="field"
                :rules="codeRules"
                maxlength="6"
              />

              <p v-if="submitError" class="submit-error">{{ submitError }}</p>
              <p v-if="submitSuccess" class="submit-success">{{ submitSuccess }}</p>

              <div class="actions">
                <v-btn
                  type="submit"
                  class="primary-btn"
                  rounded="pill"
                  elevation="0"
                  :loading="isSubmitting"
                >
                  Verifică emailul
                </v-btn>

                <v-btn
                  variant="outlined"
                  class="secondary-btn"
                  rounded="pill"
                  elevation="0"
                  :loading="isResending"
                  @click="handleResend"
                >
                  Retrimite codul
                </v-btn>
              </div>

              <div class="text-link-row">
                <button type="button" class="text-link" @click="goToLogin">
                  Înapoi la login
                </button>
              </div>
            </v-form>
          </div>
        </div>
      </v-card>
    </v-container>
  </section>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const formRef = ref(null)
const isSubmitting = ref(false)
const isResending = ref(false)
const submitError = ref('')
const submitSuccess = ref('')

const form = reactive({
  email: route.query.email || '',
  code: '',
})

const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

const emailRules = [
  v => !!v || 'Emailul este obligatoriu.',
  v => emailPattern.test(v) || 'Introdu un email valid.',
]

const codeRules = [
  v => !!v || 'Codul este obligatoriu.',
  v => /^\d{6}$/.test(v) || 'Codul trebuie să aibă 6 cifre.',
]

const handleVerify = async () => {
  submitError.value = ''
  submitSuccess.value = ''

  const result = await formRef.value?.validate()

  if (!result?.valid) {
    submitError.value = 'Te rog să completezi corect datele.'
    return
  }

  isSubmitting.value = true

  try {
    const response = await fetch('https://glow-jars-production.up.railway.app/api/auth/verify-email', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(form),
    })

    const data = await response.json()

    if (!response.ok) {
      throw new Error(data?.message || 'Nu s-a putut verifica emailul.')
    }

    submitSuccess.value = data?.message || 'Email verificat cu succes.'

    setTimeout(() => {
      router.push('/login')
    }, 1000)
  } catch (error) {
    submitError.value = error?.message || 'A apărut o eroare.'
  } finally {
    isSubmitting.value = false
  }
}

const handleResend = async () => {
  submitError.value = ''
  submitSuccess.value = ''

  const validEmail = emailPattern.test(form.email)
  if (!validEmail) {
    submitError.value = 'Introdu un email valid pentru retrimitere.'
    return
  }

  isResending.value = true

  try {
    const response = await fetch('https://glow-jars-production.up.railway.app/api/auth/resend-code', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: form.email }),
    })

    const data = await response.json()

    if (!response.ok) {
      throw new Error(data?.message || 'Nu s-a putut retrimite codul.')
    }

    submitSuccess.value = data?.message || 'Am retrimis codul.'
  } catch (error) {
    submitError.value = error?.message || 'A apărut o eroare.'
  } finally {
    isResending.value = false
  }
}

const goToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.auth-page {
  min-height: calc(100vh - 72px);
  display: flex;
  align-items: center;
  background:
    radial-gradient(circle at top left, rgba(244, 236, 233, 0.95), transparent 35%),
    radial-gradient(circle at top right, rgba(234, 221, 238, 0.9), transparent 40%),
    linear-gradient(180deg, #f5eef2 0%, #ede3eb 100%);
  padding: 56px 24px 88px;
}

.auth-card {
  width: 100%;
  max-width: 1120px;
  background: rgba(255, 255, 255, 0.58);
  backdrop-filter: blur(12px);
  border-radius: 34px;
  padding: 40px 42px;
  border: 1px solid rgba(120, 100, 120, 0.08);
  box-shadow: 0 18px 50px rgba(90, 70, 90, 0.08);
}

.auth-grid {
  display: grid;
  grid-template-columns: minmax(0, 1fr) minmax(380px, 430px);
  gap: 72px;
  align-items: center;
}

.auth-left {
  padding: 8px 8px 8px 12px;
}

.eyebrow {
  font-size: 0.96rem;
  color: #6f6469;
  margin-bottom: 18px;
}

.title {
  font-size: 3rem;
  line-height: 1.04;
  font-weight: 700;
  color: #232126;
  margin-bottom: 22px;
}

.subtitle {
  font-size: 1.08rem;
  line-height: 1.9;
  color: #6f666d;
  max-width: 470px;
}

.field {
  margin-bottom: 16px;
}

:deep(.field .v-field) {
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.5);
  min-height: 58px;
}

.submit-error {
  margin-top: 6px;
  margin-bottom: 12px;
  font-size: 0.92rem;
  color: #b23b3b;
}

.submit-success {
  margin-top: 6px;
  margin-bottom: 12px;
  font-size: 0.92rem;
  color: #2e7d32;
}

.actions {
  display: flex;
  gap: 14px;
  flex-wrap: wrap;
  margin-top: 8px;
}

.primary-btn {
  background: #d8ab45 !important;
  color: white !important;
  text-transform: none;
  font-weight: 600;
  min-height: 46px;
  padding-inline: 24px;
}

.secondary-btn {
  border-color: rgba(55, 44, 50, 0.35) !important;
  color: #3d3439 !important;
  text-transform: none;
  font-weight: 500;
  min-height: 46px;
  padding-inline: 22px;
}

.text-link-row {
  margin-top: 16px;
}

.text-link {
  background: none;
  border: none;
  padding: 0;
  color: #6a6268;
  cursor: pointer;
  text-decoration: underline;
}

@media (max-width: 960px) {
  .auth-card {
    max-width: 760px;
    padding: 30px 26px;
    border-radius: 28px;
  }

  .auth-grid {
    grid-template-columns: 1fr;
    gap: 36px;
  }

  .title {
    font-size: 2.4rem;
  }
}

@media (max-width: 600px) {
  .auth-page {
    padding: 24px 12px 44px;
  }

  .auth-card {
    padding: 20px 16px;
    border-radius: 22px;
  }

  .actions {
    flex-direction: column;
  }

  .primary-btn,
  .secondary-btn {
    width: 100%;
  }
}
</style>