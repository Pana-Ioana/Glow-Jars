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

          <v-text-field
            v-model="email"
            label="Email"
            variant="outlined"
            prepend-inner-icon="mdi-email-outline"
            class="soft-field"
            hide-details
          />

          <v-text-field
            v-model="password"
            label="Parolă"
            :type="showPassword ? 'text' : 'password'"
            variant="outlined"
            prepend-inner-icon="mdi-lock-outline"
            :append-inner-icon="showPassword ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
            @click:append-inner="showPassword = !showPassword"
            class="soft-field mt-4"
            hide-details
          />

          <div class="login-actions">

            <v-btn class="gold-btn" @click="login">
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

        </div>

      </div>

    </div>
  </v-container>
</template>

<script>
export default {
  name: "LoginView",

  data() {
    return {
      email: "",
      password: "",
      showPassword: false
    }
  },

  methods: {
    login() {
      console.log(this.email, this.password)
    }
  }
}
</script>

<style scoped>

.login-page{
  min-height: calc(100vh - 72px);
}

/* centrare reala */
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

  box-shadow:
    0 10px 30px rgba(0,0,0,0.06);

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
  background:#d5ae58;
  color:white;
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