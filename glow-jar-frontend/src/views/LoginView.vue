<template>
  <v-container fluid class="login-page pa-0">
    <div class="login-wrapper">
      <div class="login-card">
        <div class="login-left">
          <div class="eyebrow">Contul tău Glow Jar</div>

          <h1 class="login-title">
            Autentificare
          </h1>

          <p class="login-description">
            Intră în cont pentru a vedea comenzile tale și jar-urile salvate.
          </p>

          <div class="login-badges">
            <span>Mystery controlat</span>
            <span>Drops limitate</span>
            <span>Experiență boutique</span>
          </div>
        </div>

        <div class="login-right">
          <v-form ref="formRef" @submit.prevent="handleLogin">
            <v-text-field
              v-model.trim="form.email"
              label="Email"
              type="email"
              variant="outlined"
              prepend-inner-icon="mdi-email-outline"
              class="soft-field"
              :rules="emailRules"
            />

            <v-text-field
              v-model="form.password"
              label="Parolă"
              :type="showPassword ? 'text' : 'password'"
              variant="outlined"
              prepend-inner-icon="mdi-lock-outline"
              :append-inner-icon="showPassword ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
              @click:append-inner="showPassword = !showPassword"
              class="soft-field mt-4"
              :rules="passwordRules"
            />

            <p v-if="submitError" class="submit-error">
              {{ submitError }}
            </p>

            <p v-if="submitSuccess" class="submit-success">
              {{ submitSuccess }}
            </p>

            <div class="login-actions">
              <v-btn
                class="gold-btn"
                type="submit"
                :loading="isSubmitting"
                :disabled="isSubmitting"
              >
                Autentificare
              </v-btn>

              <v-btn
                variant="outlined"
                class="outline-btn"
                @click="$router.push('/')"
              >
                Înapoi la magazin
              </v-btn>
            </div>

            <p class="switch-auth">
              Nu ai cont?
              <span @click="$router.push('/register')">Creează unul</span>
            </p>
          </v-form>
        </div>
      </div>
    </div>
  </v-container>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const formRef = ref(null)
const isSubmitting = ref(false)
const submitError = ref('')
const submitSuccess = ref('')
const showPassword = ref(false)

const form = reactive({
  email: '',
  password: '',
})

const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

const emailRules = [
  v => !!v || 'Emailul este obligatoriu.',
  v => emailPattern.test(v) || 'Introdu un email valid.',
]

const passwordRules = [
  v => !!v || 'Parola este obligatorie.',
]

const resetForm = () => {
  form.email = ''
  form.password = ''
  formRef.value?.resetValidation?.()
}

const handleLogin = async () => {
  submitError.value = ''
  submitSuccess.value = ''

  const result = await formRef.value?.validate()

  if (!result?.valid) {
    submitError.value = 'Te rog să completezi corect câmpurile.'
    return
  }

  isSubmitting.value = true

  try {
    const response = await fetch('https://glow-jars-production.up.railway.app/api/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
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

      throw new Error('Autentificarea a eșuat.')
    }

    localStorage.setItem('glowJarUser', JSON.stringify(data))
    submitSuccess.value = 'Te-ai autentificat cu succes.'

    resetForm()

    setTimeout(() => {
      router.push('/')
    }, 900)
  } catch (error) {
    submitError.value = error?.message || 'A apărut o eroare la autentificare.'
  } finally {
    isSubmitting.value = false
  }
}
</script>

<style scoped>
.login-page{
  min-height: calc(100vh - 72px);
}

.login-wrapper{
  min-height: calc(100vh - 72px);
  display:flex;
  align-items:center;
  justify-content:center;
  padding:40px 20px;
}

.login-card{
  width:100%;
  max-width:1050px;
  background:rgba(255,255,255,0.75);
  border-radius:30px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.06);
  padding:50px;
  display:grid;
  grid-template-columns:1fr 380px;
  gap:50px;
}

.login-title{
  font-size:44px;
  margin-bottom:14px;
}

.login-description{
  font-size:18px;
  line-height:1.6;
  margin-bottom:22px;
  color:#5a4b52;
}

.login-badges{
  display:flex;
  flex-wrap:wrap;
  gap:10px;
}

.login-badges span{
  padding:8px 14px;
  border-radius:999px;
  border:1.5px solid #2f2428;
  font-size:14px;
}

.login-right{
  display:flex;
  flex-direction:column;
}

.soft-field :deep(.v-field){
  border-radius:14px;
}

.login-actions{
  margin-top:22px;
  display:flex;
  gap:12px;
  flex-wrap:wrap;
}

.gold-btn{
  background:#d5ae58 !important;
  color:white !important;
  border-radius:999px;
  text-transform:none;
  height:44px;
}

.outline-btn{
  border-radius:999px;
  text-transform:none;
  border:1.5px solid #2f2428;
  height:44px;
}

.switch-auth {
  margin-top: 14px;
  font-size: 0.95rem;
  color: #6d6369;
}

.switch-auth span {
  color: #b8872e;
  font-weight: 600;
  cursor: pointer;
}

.submit-error {
  margin-top: 10px;
  margin-bottom: 0;
  font-size: 0.92rem;
  color: #b23b3b;
}

.submit-success {
  margin-top: 10px;
  margin-bottom: 0;
  font-size: 0.92rem;
  color: #2e7d32;
}

@media (max-width:900px){
  .login-card{
    grid-template-columns:1fr;
    padding:32px;
    gap:30px;
  }

  .login-title{
    font-size:34px;
  }
}
</style>