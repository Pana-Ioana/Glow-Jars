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
          </div>

          <div class="register-right">
            <v-form ref="formRef" @submit.prevent="handleRegister">
              <v-text-field
                v-model.trim="form.firstName"
                label="Prenume"
                variant="outlined"
                class="field"
                :rules="requiredRule"
              />

              <v-text-field
                v-model.trim="form.lastName"
                label="Nume"
                variant="outlined"
                class="field"
                :rules="requiredRule"
              />

              <v-text-field
                v-model.trim="form.email"
                label="Email"
                type="email"
                variant="outlined"
                class="field"
                :rules="emailRules"
              />

              <v-text-field
                v-model="form.password"
                :type="showPassword ? 'text' : 'password'"
                label="Parolă"
                variant="outlined"
                class="field"
                :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
                @click:append-inner="showPassword = !showPassword"
                :rules="passwordRules"
              />

              <v-text-field
                v-model="form.confirmPassword"
                :type="showConfirmPassword ? 'text' : 'password'"
                label="Confirmă parola"
                variant="outlined"
                class="field"
                :append-inner-icon="showConfirmPassword ? 'mdi-eye-off' : 'mdi-eye'"
                @click:append-inner="showConfirmPassword = !showConfirmPassword"
                :rules="confirmPasswordRules"
              />

              <p v-if="submitError" class="submit-error">{{ submitError }}</p>
              <p v-if="submitSuccess" class="submit-success">{{ submitSuccess }}</p>

              <v-btn
                type="submit"
                class="register-btn"
                :loading="isSubmitting"
                block
              >
                Creează cont
              </v-btn>
              
            </v-form>
            
          </div>
        </div>
      </v-card>
    </v-container>
  </section>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const formRef = ref(null)
const isSubmitting = ref(false)
const submitError = ref('')
const submitSuccess = ref('')
const showPassword = ref(false)
const showConfirmPassword = ref(false)

const form = reactive({
  firstName: '',
  lastName: '',
  email: '',
  password: '',
  confirmPassword: '',
})


const requiredRule = [
  v => !!v || 'Câmp obligatoriu',
]

const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

const emailRules = [
  v => !!v || 'Email obligatoriu',
  v => emailPattern.test(v) || 'Email invalid',
]

const passwordRules = [
  v => !!v || 'Parola obligatorie',
  v => v.length >= 8 || 'Minim 8 caractere',
]

const confirmPasswordRules = computed(() => [
  v => !!v || 'Confirmă parola',
  v => v === form.password || 'Parolele nu se potrivesc',
])


const handleRegister = async () => {
  submitError.value = ''
  submitSuccess.value = ''

  const result = await formRef.value?.validate()

  if (!result?.valid) {
    submitError.value = 'Completează corect câmpurile.'
    return
  }

  isSubmitting.value = true

  try {
    const registeredEmail = form.email

    const response = await fetch('http://localhost:8080/api/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
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
      data = { message: rawText }
    }

    if (!response.ok) {
      throw new Error(data?.message || 'Eroare la creare cont')
    }

    submitSuccess.value = data?.message || 'Ți-am trimis cod pe email.'

    console.log('GO VERIFY PAGE', registeredEmail)

    router.push({
      path: '/verify-email',
      query: { email: registeredEmail },
    })

  } catch (error) {
    submitError.value = error.message || 'Eroare server'
  } finally {
    isSubmitting.value = false
  }

  const loginWithGoogle = () => {
  window.location.href = 'http://localhost:8080/oauth2/authorization/google'
}
}
</script>

<style scoped>
.register-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  background: linear-gradient(180deg, #f5eef2, #ede3eb);
}

.register-card {
  width: 100%;
  max-width: 900px;
  padding: 40px;
  border-radius: 30px;
}

.register-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
}

.subtitle {
  margin-top: 10px;
  color: #666;
}

.field {
  margin-bottom: 14px;
}

.register-btn {
  background: #d8ab45;
  color: white;
  margin-top: 10px;
}

.submit-error {
  color: red;
}

.submit-success {
  color: green;
}
</style>