<template>
  <section class="register-page">
    <v-container class="fill-height d-flex align-center justify-center">
      <v-card class="register-card" elevation="0">
        <div class="register-grid">
          <div class="register-left">
            <p class="eyebrow">Cont nou Glow Jar</p>

            <h1 class="title">Creează cont</h1>

            <p class="subtitle">
              Fă-ți un cont pentru a salva jar-uri, a urmări comenzile și a reveni
              rapid la selecțiile tale preferate.
            </p>

            <div class="chips">
              <span class="feature-chip">Wishlist boutique</span>
              <span class="feature-chip">Comenzi salvate</span>
              <span class="feature-chip">Drops exclusive</span>
            </div>
          </div>

          <div class="register-right">
            <v-form ref="formRef" @submit.prevent="handleRegister">
              <v-text-field
                v-model.trim="form.firstName"
                label="Prenume"
                variant="outlined"
                density="comfortable"
                class="field"
                :rules="firstNameRules"
              />

              <v-text-field
                v-model.trim="form.lastName"
                label="Nume"
                variant="outlined"
                density="comfortable"
                class="field"
                :rules="lastNameRules"
              />

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
                v-model="form.password"
                :type="showPassword ? 'text' : 'password'"
                label="Parolă"
                variant="outlined"
                density="comfortable"
                class="field"
                :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
                :rules="passwordRules"
                @click:append-inner="showPassword = !showPassword"
              />

              <v-text-field
                v-model="form.confirmPassword"
                :type="showConfirmPassword ? 'text' : 'password'"
                label="Confirmă parola"
                variant="outlined"
                density="comfortable"
                class="field"
                :append-inner-icon="showConfirmPassword ? 'mdi-eye-off' : 'mdi-eye'"
                :rules="confirmPasswordRules"
                @click:append-inner="showConfirmPassword = !showConfirmPassword"
              />

              <v-checkbox
                v-model="acceptTerms"
                density="compact"
                class="terms-checkbox"
                :rules="termsRules"
              >
                <template #label>
                  <span class="terms-text">
                    Sunt de acord cu termenii și politica de confidențialitate
                  </span>
                </template>
              </v-checkbox>

              <p v-if="submitError" class="submit-error">
                {{ submitError }}
              </p>

              <p v-if="submitSuccess" class="submit-success">
                {{ submitSuccess }}
              </p>

              <div class="actions">
                <v-btn
                  type="submit"
                  class="register-btn"
                  rounded="pill"
                  elevation="0"
                  :loading="isSubmitting"
                  :disabled="isSubmitting"
                >
                  Creează cont
                </v-btn>

                <v-btn
                  variant="outlined"
                  class="back-btn"
                  rounded="pill"
                  elevation="0"
                  @click="goToLogin"
                >
                  Am deja cont
                </v-btn>
              </div>
            </v-form>
          </div>
        </div>
      </v-card>
    </v-container>
  </section>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const formRef = ref(null)
const isSubmitting = ref(false)
const submitError = ref('')
const submitSuccess = ref('')
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const acceptTerms = ref(false)

const form = reactive({
  firstName: '',
  lastName: '',
  email: '',
  password: '',
  confirmPassword: '',
})

const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

const firstNameRules = [
  v => !!v || 'Prenumele este obligatoriu.',
  v => (v && v.length >= 2) || 'Prenumele trebuie să aibă minim 2 caractere.',
]

const lastNameRules = [
  v => !!v || 'Numele este obligatoriu.',
  v => (v && v.length >= 2) || 'Numele trebuie să aibă minim 2 caractere.',
]

const emailRules = [
  v => !!v || 'Emailul este obligatoriu.',
  v => emailPattern.test(v) || 'Introdu un email valid.',
]

const passwordRules = [
  v => !!v || 'Parola este obligatorie.',
  v => (v && v.length >= 8) || 'Parola trebuie să aibă minim 8 caractere.',
  v => /[A-Z]/.test(v) || 'Parola trebuie să conțină cel puțin o literă mare.',
  v => /[0-9]/.test(v) || 'Parola trebuie să conțină cel puțin o cifră.',
]

const confirmPasswordRules = computed(() => [
  v => !!v || 'Confirmarea parolei este obligatorie.',
  v => v === form.password || 'Parolele nu se potrivesc.',
])

const termsRules = [
  v => !!v || 'Trebuie să accepți termenii pentru a continua.',
]

const resetForm = () => {
  form.firstName = ''
  form.lastName = ''
  form.email = ''
  form.password = ''
  form.confirmPassword = ''
  acceptTerms.value = false
  formRef.value?.resetValidation?.()
}

const handleRegister = async () => {
  submitError.value = ''
  submitSuccess.value = ''

  const result = await formRef.value?.validate()

  if (!result?.valid) {
    submitError.value = 'Te rog să corectezi câmpurile marcate.'
    return
  }

  isSubmitting.value = true

  try {
    const response = await fetch('https://glow-jars.vercel.app/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        firstName: form.firstName,
        lastName: form.lastName,
        email: form.email,
        password: form.password,
      }),
    })

    const rawText = await response.text()
    let data = null

    try {
      data = rawText ? JSON.parse(rawText) : null
    } catch {
      data = rawText
    }

    if (!response.ok) {
      if (typeof data === 'string' && data.trim()) {
        throw new Error(data)
      }

      if (data?.message) {
        throw new Error(data.message)
      }

      throw new Error('Nu s-a putut crea contul.')
    }

    localStorage.setItem('glowJarUser', JSON.stringify(data))
    submitSuccess.value = 'Cont creat cu succes. Te redirecționez...'

    resetForm()

    setTimeout(() => {
      router.push('/')
    }, 900)
  } catch (error) {
    submitError.value = error?.message || 'A apărut o eroare la înregistrare.'
  } finally {
    isSubmitting.value = false
  }
}

const goToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.register-page {
  min-height: calc(100vh - 72px);
  display: flex;
  align-items: center;
  background:
    radial-gradient(circle at top left, rgba(244, 236, 233, 0.95), transparent 35%),
    radial-gradient(circle at top right, rgba(234, 221, 238, 0.9), transparent 40%),
    linear-gradient(180deg, #f5eef2 0%, #ede3eb 100%);
  padding: 56px 24px 88px;
}

.register-card {
  width: 100%;
  max-width: 1120px;
  background: rgba(255, 255, 255, 0.58);
  backdrop-filter: blur(12px);
  border-radius: 34px;
  padding: 40px 42px;
  border: 1px solid rgba(120, 100, 120, 0.08);
  box-shadow: 0 18px 50px rgba(90, 70, 90, 0.08);
}

.register-grid {
  display: grid;
  grid-template-columns: minmax(0, 1fr) minmax(380px, 430px);
  gap: 72px;
  align-items: center;
}

.register-left {
  padding: 8px 8px 8px 12px;
}

.eyebrow {
  font-size: 0.96rem;
  color: #6f6469;
  margin-bottom: 18px;
}

.title {
  font-size: 3.2rem;
  line-height: 1.04;
  font-weight: 700;
  color: #232126;
  margin-bottom: 22px;
  max-width: 430px;
}

.subtitle {
  font-size: 1.08rem;
  line-height: 1.9;
  color: #6f666d;
  max-width: 470px;
  margin-bottom: 30px;
}

.chips {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  max-width: 460px;
}

.feature-chip {
  padding: 10px 16px;
  border: 1px solid rgba(60, 47, 55, 0.35);
  border-radius: 999px;
  font-size: 0.92rem;
  color: #4f434a;
  background: rgba(255, 255, 255, 0.52);
}

.register-right {
  padding: 8px 0;
}

.register-right form {
  width: 100%;
}

.field {
  margin-bottom: 16px;
}

:deep(.field .v-field) {
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.5);
  min-height: 58px;
}

:deep(.field .v-field__input) {
  padding-top: 18px;
  padding-bottom: 18px;
}

:deep(.field .v-field__outline) {
  --v-field-border-opacity: 0.28;
}

:deep(.field input) {
  color: #2a2529;
}

.terms-checkbox {
  margin-top: 6px;
  margin-bottom: 10px;
}

.terms-text {
  font-size: 0.93rem;
  color: #6a6268;
  line-height: 1.55;
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

.register-btn {
  background: #d8ab45 !important;
  color: white !important;
  text-transform: none;
  font-weight: 600;
  letter-spacing: 0;
  min-height: 46px;
  padding-inline: 24px;
}

.back-btn {
  border-color: rgba(55, 44, 50, 0.35) !important;
  color: #3d3439 !important;
  text-transform: none;
  font-weight: 500;
  letter-spacing: 0;
  background: transparent;
  min-height: 46px;
  padding-inline: 22px;
}

@media (max-width: 960px) {
  .register-page {
    padding: 40px 18px 64px;
  }

  .register-card {
    max-width: 760px;
    padding: 30px 26px;
    border-radius: 28px;
  }

  .register-grid {
    grid-template-columns: 1fr;
    gap: 36px;
  }

  .title {
    font-size: 2.45rem;
    max-width: 100%;
  }

  .subtitle,
  .chips {
    max-width: 100%;
  }
}

@media (max-width: 600px) {
  .register-page {
    padding: 24px 12px 44px;
  }

  .register-card {
    padding: 20px 16px;
    border-radius: 22px;
  }

  .title {
    font-size: 2rem;
  }

  .subtitle {
    font-size: 0.98rem;
    line-height: 1.7;
    margin-bottom: 22px;
  }

  .register-grid {
    gap: 26px;
  }

  .field {
    margin-bottom: 14px;
  }

  :deep(.field .v-field) {
    min-height: 54px;
    border-radius: 16px;
  }

  .actions {
    flex-direction: column;
  }

  .register-btn,
  .back-btn {
    width: 100%;
  }
}
</style>