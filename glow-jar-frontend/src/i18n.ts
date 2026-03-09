import { createI18n } from "vue-i18n"

const messages = {
  ro: {
    nav: {
      collection: "Colecție",
      drops: "Drops",
      howItWorks: "Cum funcționează",
      login: "Autentificare",
      createJar: "Creează jar",
    },
    home: {
      brandSubtitle: "Experiență boutique curată",
      heroTitle: "Curated Mystery Jewelry",
      heroSubtitle:
        "Surprize fine. Împachetate cu grijă. Alege o estetică, selectează un nivel și lasă-ne să îți curăm surpriza.",
      seeCollection: "Vezi colecția",
      seeAll: "Vezi toate",
      controlledMystery: "Mystery controlat",
      guaranteedValue: "Valoare garantată",
      rareUpgrade: "Upgrade rar",
      limitedDrops: "Drops limitate",
      categoriesTitle: "Estetici / categorii",
      categoriesSubtitle: "Vezi toate stilurile sau intră pe o categorie.",
      popularTitle: "Cele mai populare",
      popularSubtitle: "Un preview cu cele mai cumpărate jar-uri.",
      howTitle: "Cum funcționează",
      step1Title: "Alegi estetica",
      step1Text: "Selectezi un stil și un nivel.",
      step2Title: "Noi curăm",
      step2Text: "Surpriză controlată, cu vibe boutique.",
      step3Title: "Tu desfaci",
      step3Text: "Primești jar-ul și poți prinde upgrade-uri rare.",
      limitedCollection: "COLECȚIE LIMITATĂ",
      dropText: "Drop special disponibil pentru o perioadă limitată.",
      seeDrop: "Vezi drop",
      exploreCollection: "Explorează colecția",
      categoryCards: {
        all: {
          title: "Toate jar-urile",
          description: "Vezi colecția completă.",
        },
        romantic: {
          title: "Romantic & pastel",
          description: "Stiluri delicate și feminine.",
        },
        bold: {
          title: "Statement & glam",
          description: "Strălucire și energie.",
        },
      },
      bestsellers: {
        soft: "Univers delicat cu tonuri pastel.",
        golden: "Eleganță aurie sofisticată.",
        dark: "Vibe misterios și dramatic.",
      },
    },
    footer: {
      subscribe: "Abonează-te la emailurile noastre",
      emailPlaceholder: "Email",
      followShop: "Urmărește-ne",
      country: "Țară/regiune",
      language: "Limbă",
      copyright: "© 2026 Glow Jar",
    },
    collection: {
      title: "Toate jar-urile",
      subtitle:
        "Explorează întreaga colecție Glow Jar și descoperă stilul care ți se potrivește.",
      filterLabel: "Filtrează după stil",
      imagePlaceholder: "Imagine jar",
      discover: "Descoperă",
      from: "de la",
      filters: {
        all: "Toate",
        romantic: "Romantic & pastel",
        minimal: "Minimal & clean",
        elegant: "Elegant & clasic",
        bold: "Bold & statement",
        dark: "Dark & mysterious",
        boho: "Boho & natural",
        glam: "Glam & festive",
        celestial: "Celestial & dreamy",
        vintage: "Vintage inspired",
      },
      items: {
        soft: {
          description:
            "Un univers delicat, cu accente romantice și tonuri pastel care completează un stil feminin și visător.",
        },
        golden: {
          description:
            "O estetică inspirată de eleganța aurie, cu o energie caldă și sofisticată, potrivită pentru apariții memorabile.",
        },
        minimal: {
          description:
            "Linii curate, echilibru și rafinament discret pentru un stil modern care nu are nevoie de exagerări.",
        },
        dark: {
          description:
            "Un vibe profund și misterios, cu accente dramatice și o eleganță care atrage fără să fie evidentă.",
        },
        rose: {
          description:
            "O estetică luminoasă și romantică, inspirată de nuanțe fine de roz și o energie blândă, feminină.",
        },
        celestial: {
          description:
            "Inspirat de cerul nopții și de lumina subtilă a stelelor, pentru un stil visător și ușor misterios.",
        },
        vintage: {
          description:
            "Un mix între clasic și modern, cu influențe retro reinterpretate într-o manieră elegantă și actuală.",
        },
        cottage: {
          description:
            "O atmosferă naturală și romantică, inspirată de flori, lumină caldă și simplitate autentică.",
        },
        edgy: {
          description:
            "Un stil îndrăzneț și modern, cu accente metalice și o energie urbană care nu trece neobservată.",
        },
        boho: {
          description:
            "O estetică liberă și artistică, cu influențe boeme și o energie relaxată, perfectă pentru spiritul liber.",
        },
        glam: {
          description:
            "Strălucire și opulență pentru un stil care emană încredere și atracție, perfect pentru ocazii speciale.",
        },
      },
    },
  },

  en: {
    nav: {
      collection: "Collection",
      drops: "Drops",
      howItWorks: "How it works",
      login: "Log in",
      createJar: "Create jar",
    },
    home: {
      brandSubtitle: "Clean boutique experience",
      heroTitle: "Curated Mystery Jewelry",
      heroSubtitle:
        "Soft surprises. Thoughtfully packed. Choose an aesthetic, select a tier, and let us curate your surprise.",
      seeCollection: "See collection",
      seeAll: "See all",
      controlledMystery: "Controlled mystery",
      guaranteedValue: "Guaranteed value",
      rareUpgrade: "Rare upgrade",
      limitedDrops: "Limited drops",
      categoriesTitle: "Aesthetics / categories",
      categoriesSubtitle: "See all styles or enter a category.",
      popularTitle: "Most popular",
      popularSubtitle: "A preview of the most loved jars.",
      howTitle: "How it works",
      step1Title: "Choose the aesthetic",
      step1Text: "Select a style and a tier.",
      step2Title: "We curate",
      step2Text: "A controlled surprise with a boutique vibe.",
      step3Title: "You unbox",
      step3Text: "Receive your jar and maybe catch rare upgrades.",
      limitedCollection: "LIMITED COLLECTION",
      dropText: "A special drop available for a limited time.",
      seeDrop: "See drop",
      exploreCollection: "Explore the collection",
      categoryCards: {
        all: {
          title: "All jars",
          description: "See the full collection.",
        },
        romantic: {
          title: "Romantic & pastel",
          description: "Soft and feminine styles.",
        },
        bold: {
          title: "Statement & glam",
          description: "Shine and energy.",
        },
      },
      bestsellers: {
        soft: "A delicate universe with pastel tones.",
        golden: "Sophisticated golden elegance.",
        dark: "A mysterious and dramatic vibe.",
      },
    },
    footer: {
      subscribe: "Subscribe to our emails",
      emailPlaceholder: "Email",
      followShop: "Follow us",
      country: "Country/region",
      language: "Language",
      copyright: "© 2026 Glow Jar",
    },
    collection: {
      title: "All jars",
      subtitle:
        "Explore the full Glow Jar collection and discover the style that suits you.",
      filterLabel: "Filter by style",
      imagePlaceholder: "Jar image",
      discover: "Discover",
      from: "from",
      filters: {
        all: "All",
        romantic: "Romantic & pastel",
        minimal: "Minimal & clean",
        elegant: "Elegant & classic",
        bold: "Bold & statement",
        dark: "Dark & mysterious",
        boho: "Boho & natural",
        glam: "Glam & festive",
        celestial: "Celestial & dreamy",
        vintage: "Vintage inspired",
      },
      items: {
        soft: {
          description:
            "A delicate universe with romantic accents and pastel tones for a soft, dreamy style.",
        },
        golden: {
          description:
            "An aesthetic inspired by golden elegance, with a warm and sophisticated energy.",
        },
        minimal: {
          description:
            "Clean lines, balance and discreet refinement for a modern style without excess.",
        },
        dark: {
          description:
            "A deep and mysterious vibe with dramatic accents and understated elegance.",
        },
        rose: {
          description:
            "A luminous romantic aesthetic inspired by soft pink shades and a gentle feminine energy.",
        },
        celestial: {
          description:
            "Inspired by the night sky and subtle starlight, for a dreamy and slightly mysterious style.",
        },
        vintage: {
          description:
            "A blend of classic and modern, with retro influences reinterpreted in an elegant way.",
        },
        cottage: {
          description:
            "A natural and romantic atmosphere inspired by flowers, warm light and authentic simplicity.",
        },
        edgy: {
          description:
            "A bold modern style with metallic accents and an urban energy that stands out.",
        },
        boho: {
          description:
            "A free-spirited artistic aesthetic with bohemian influences and a relaxed energy.",
        },
        glam: {
          description:
            "Shine and opulence for a style that radiates confidence and attraction.",
        },
      },
    },
  },

  fr: {
    nav: {
      collection: "Collection",
      drops: "Drops",
      howItWorks: "Comment ça marche",
      login: "Connexion",
      createJar: "Créer un jar",
    },
    home: {
      brandSubtitle: "Expérience boutique épurée",
      heroTitle: "Curated Mystery Jewelry",
      heroSubtitle:
        "De douces surprises, préparées avec soin. Choisissez une esthétique, sélectionnez un niveau et laissez-nous composer votre surprise.",
      seeCollection: "Voir la collection",
      seeAll: "Voir tout",
      controlledMystery: "Mystère contrôlé",
      guaranteedValue: "Valeur garantie",
      rareUpgrade: "Upgrade rare",
      limitedDrops: "Drops limités",
      categoriesTitle: "Esthétiques / catégories",
      categoriesSubtitle: "Découvrez tous les styles ou entrez dans une catégorie.",
      popularTitle: "Les plus populaires",
      popularSubtitle: "Un aperçu des jars les plus appréciés.",
      howTitle: "Comment ça marche",
      step1Title: "Choisissez l’esthétique",
      step1Text: "Sélectionnez un style et un niveau.",
      step2Title: "Nous composons",
      step2Text: "Une surprise contrôlée avec une touche boutique.",
      step3Title: "Vous déballez",
      step3Text: "Recevez votre jar et tentez des upgrades rares.",
      limitedCollection: "COLLECTION LIMITÉE",
      dropText: "Un drop spécial disponible pour une période limitée.",
      seeDrop: "Voir le drop",
      exploreCollection: "Explorer la collection",
      categoryCards: {
        all: {
          title: "Tous les jars",
          description: "Voir toute la collection.",
        },
        romantic: {
          title: "Romantique & pastel",
          description: "Des styles doux et féminins.",
        },
        bold: {
          title: "Statement & glam",
          description: "Éclat et énergie.",
        },
      },
      bestsellers: {
        soft: "Un univers délicat aux tons pastel.",
        golden: "Une élégance dorée sophistiquée.",
        dark: "Une ambiance mystérieuse et dramatique.",
      },
    },
    footer: {
      subscribe: "Abonnez-vous à nos emails",
      emailPlaceholder: "Email",
      followShop: "Suivez-nous",
      country: "Pays/région",
      language: "Langue",
      copyright: "© 2026 Glow Jar",
    },
    collection: {
      title: "Tous les jars",
      subtitle:
        "Explorez toute la collection Glow Jar et découvrez le style qui vous correspond.",
      filterLabel: "Filtrer par style",
      imagePlaceholder: "Image du jar",
      discover: "Découvrir",
      from: "à partir de",
      filters: {
        all: "Tous",
        romantic: "Romantique & pastel",
        minimal: "Minimal & clean",
        elegant: "Élégant & classique",
        bold: "Bold & statement",
        dark: "Sombre & mystérieux",
        boho: "Boho & naturel",
        glam: "Glam & festif",
        celestial: "Céleste & rêveur",
        vintage: "Inspiration vintage",
      },
      items: {
        soft: { description: "Un univers délicat aux accents romantiques et aux tons pastel." },
        golden: { description: "Une esthétique inspirée par l’élégance dorée et sophistiquée." },
        minimal: { description: "Des lignes épurées et un raffinement discret pour un style moderne." },
        dark: { description: "Une ambiance profonde et mystérieuse avec des accents dramatiques." },
        rose: { description: "Une esthétique romantique et lumineuse inspirée par des tons roses délicats." },
        celestial: { description: "Inspiré par le ciel nocturne et la lumière subtile des étoiles." },
        vintage: { description: "Un mélange de classique et de moderne avec des influences rétro." },
        cottage: { description: "Une atmosphère naturelle et romantique inspirée par les fleurs." },
        edgy: { description: "Un style audacieux et moderne avec une énergie urbaine marquée." },
        boho: { description: "Une esthétique libre et artistique avec des influences bohèmes." },
        glam: { description: "Brillance et opulence pour un style qui inspire confiance." },
      },
    },
  },

  de: {
    nav: {
      collection: "Kollektion",
      drops: "Drops",
      howItWorks: "So funktioniert's",
      login: "Anmelden",
      createJar: "Jar erstellen",
    },
    home: {
      brandSubtitle: "Cleane Boutique-Erfahrung",
      heroTitle: "Curated Mystery Jewelry",
      heroSubtitle:
        "Feine Überraschungen, liebevoll verpackt. Wähle eine Ästhetik, ein Level und lass uns deine Überraschung kuratieren.",
      seeCollection: "Kollektion ansehen",
      seeAll: "Alle ansehen",
      controlledMystery: "Kontrolliertes Mystery",
      guaranteedValue: "Garantierter Wert",
      rareUpgrade: "Seltenes Upgrade",
      limitedDrops: "Limitierte Drops",
      categoriesTitle: "Ästhetiken / Kategorien",
      categoriesSubtitle: "Sieh dir alle Stile an oder öffne eine Kategorie.",
      popularTitle: "Am beliebtesten",
      popularSubtitle: "Eine Vorschau der meistgekauften Jars.",
      howTitle: "So funktioniert's",
      step1Title: "Ästhetik wählen",
      step1Text: "Wähle einen Stil und ein Level.",
      step2Title: "Wir kuratieren",
      step2Text: "Eine kontrollierte Überraschung mit Boutique-Vibe.",
      step3Title: "Du packst aus",
      step3Text: "Du erhältst dein Jar und kannst seltene Upgrades bekommen.",
      limitedCollection: "LIMITIERTE KOLLEKTION",
      dropText: "Ein spezieller Drop, nur für begrenzte Zeit verfügbar.",
      seeDrop: "Drop ansehen",
      exploreCollection: "Kollektion entdecken",
      categoryCards: {
        all: {
          title: "Alle Jars",
          description: "Die komplette Kollektion ansehen.",
        },
        romantic: {
          title: "Romantisch & pastel",
          description: "Sanfte und feminine Stile.",
        },
        bold: {
          title: "Statement & glam",
          description: "Glanz und Energie.",
        },
      },
      bestsellers: {
        soft: "Eine zarte Welt in Pastelltönen.",
        golden: "Sofort erkennbare goldene Eleganz.",
        dark: "Ein geheimnisvoller, dramatischer Vibe.",
      },
    },
    footer: {
      subscribe: "Abonniere unsere Emails",
      emailPlaceholder: "Email",
      followShop: "Folge uns",
      country: "Land/Region",
      language: "Sprache",
      copyright: "© 2026 Glow Jar",
    },
    collection: {
      title: "Alle Jars",
      subtitle:
        "Entdecke die gesamte Glow Jar Kollektion und finde den Stil, der zu dir passt.",
      filterLabel: "Nach Stil filtern",
      imagePlaceholder: "Jar-Bild",
      discover: "Entdecken",
      from: "ab",
      filters: {
        all: "Alle",
        romantic: "Romantisch & pastel",
        minimal: "Minimal & clean",
        elegant: "Elegant & klassisch",
        bold: "Bold & statement",
        dark: "Dunkel & geheimnisvoll",
        boho: "Boho & natürlich",
        glam: "Glam & festlich",
        celestial: "Celestial & dreamy",
        vintage: "Vintage inspiriert",
      },
      items: {
        soft: { description: "Eine zarte Ästhetik mit romantischen Akzenten und Pastelltönen." },
        golden: { description: "Eine Ästhetik, inspiriert von goldener und raffinierter Eleganz." },
        minimal: { description: "Klare Linien und dezente Raffinesse für einen modernen Stil." },
        dark: { description: "Ein tiefer, geheimnisvoller Vibe mit dramatischen Akzenten." },
        rose: { description: "Eine romantische, leuchtende Ästhetik mit sanften Rosatönen." },
        celestial: { description: "Inspiriert vom Nachthimmel und dem subtilen Licht der Sterne." },
        vintage: { description: "Eine Mischung aus klassisch und modern mit Retro-Einflüssen." },
        cottage: { description: "Eine natürliche und romantische Atmosphäre, inspiriert von Blumen." },
        edgy: { description: "Ein mutiger moderner Stil mit urbaner Energie." },
        boho: { description: "Eine freie, künstlerische Ästhetik mit Boho-Einflüssen." },
        glam: { description: "Glanz und Opulenz für einen Stil voller Selbstbewusstsein." },
      },
    },
  },

  it: {
    nav: {
      collection: "Collezione",
      drops: "Drops",
      howItWorks: "Come funziona",
      login: "Accedi",
      createJar: "Crea jar",
    },
    home: {
      brandSubtitle: "Esperienza boutique pulita",
      heroTitle: "Curated Mystery Jewelry",
      heroSubtitle:
        "Sorprese raffinate, confezionate con cura. Scegli un’estetica, seleziona un livello e lascia che curiamo la tua sorpresa.",
      seeCollection: "Vedi collezione",
      seeAll: "Vedi tutto",
      controlledMystery: "Mystery controllato",
      guaranteedValue: "Valore garantito",
      rareUpgrade: "Upgrade raro",
      limitedDrops: "Drops limitati",
      categoriesTitle: "Estetiche / categorie",
      categoriesSubtitle: "Scopri tutti gli stili o entra in una categoria.",
      popularTitle: "I più popolari",
      popularSubtitle: "Un’anteprima dei jar più acquistati.",
      howTitle: "Come funziona",
      step1Title: "Scegli l’estetica",
      step1Text: "Selezioni uno stile e un livello.",
      step2Title: "Noi curiamo",
      step2Text: "Una sorpresa controllata con vibe boutique.",
      step3Title: "Tu apri",
      step3Text: "Ricevi il tuo jar e puoi trovare upgrade rari.",
      limitedCollection: "COLLEZIONE LIMITATA",
      dropText: "Drop speciale disponibile per un periodo limitato.",
      seeDrop: "Vedi drop",
      exploreCollection: "Esplora la collezione",
      categoryCards: {
        all: {
          title: "Tutti i jar",
          description: "Vedi l’intera collezione.",
        },
        romantic: {
          title: "Romantico & pastel",
          description: "Stili delicati e femminili.",
        },
        bold: {
          title: "Statement & glam",
          description: "Brillantezza ed energia.",
        },
      },
      bestsellers: {
        soft: "Un universo delicato dai toni pastello.",
        golden: "Eleganza dorata sofisticata.",
        dark: "Un vibe misterioso e drammatico.",
      },
    },
    footer: {
      subscribe: "Iscriviti alle nostre email",
      emailPlaceholder: "Email",
      followShop: "Seguici",
      country: "Paese/regione",
      language: "Lingua",
      copyright: "© 2026 Glow Jar",
    },
    collection: {
      title: "Tutti i jar",
      subtitle:
        "Esplora l’intera collezione Glow Jar e scopri lo stile che fa per te.",
      filterLabel: "Filtra per stile",
      imagePlaceholder: "Immagine jar",
      discover: "Scopri",
      from: "da",
      filters: {
        all: "Tutti",
        romantic: "Romantico & pastel",
        minimal: "Minimal & clean",
        elegant: "Elegante & classico",
        bold: "Bold & statement",
        dark: "Dark & mysterious",
        boho: "Boho & naturale",
        glam: "Glam & festivo",
        celestial: "Celestial & dreamy",
        vintage: "Vintage inspired",
      },
      items: {
        soft: { description: "Un universo delicato con accenti romantici e toni pastello." },
        golden: { description: "Un’estetica ispirata all’eleganza dorata e sofisticata." },
        minimal: { description: "Linee pulite e raffinatezza discreta per uno stile moderno." },
        dark: { description: "Un vibe profondo e misterioso con accenti drammatici." },
        rose: { description: "Un’estetica luminosa e romantica ispirata a sfumature rosa delicate." },
        celestial: { description: "Ispirato al cielo notturno e alla luce sottile delle stelle." },
        vintage: { description: "Un mix tra classico e moderno con influenze rétro." },
        cottage: { description: "Un’atmosfera naturale e romantica ispirata ai fiori." },
        edgy: { description: "Uno stile audace e moderno con energia urbana." },
        boho: { description: "Un’estetica libera e artistica con influenze bohémien." },
        glam: { description: "Brillantezza e opulenza per uno stile che trasmette sicurezza." },
      },
    },
  },

  es: {
    nav: {
      collection: "Colección",
      drops: "Drops",
      howItWorks: "Cómo funciona",
      login: "Iniciar sesión",
      createJar: "Crear jar",
    },
    home: {
      brandSubtitle: "Experiencia boutique limpia",
      heroTitle: "Curated Mystery Jewelry",
      heroSubtitle:
        "Sorpresas delicadas, preparadas con cuidado. Elige una estética, selecciona un nivel y deja que creemos tu sorpresa.",
      seeCollection: "Ver colección",
      seeAll: "Ver todo",
      controlledMystery: "Mystery controlado",
      guaranteedValue: "Valor garantizado",
      rareUpgrade: "Upgrade raro",
      limitedDrops: "Drops limitados",
      categoriesTitle: "Estéticas / categorías",
      categoriesSubtitle: "Explora todos los estilos o entra en una categoría.",
      popularTitle: "Más populares",
      popularSubtitle: "Una vista previa de los jars más comprados.",
      howTitle: "Cómo funciona",
      step1Title: "Elige la estética",
      step1Text: "Seleccionas un estilo y un nivel.",
      step2Title: "Nosotros curamos",
      step2Text: "Una sorpresa controlada con vibe boutique.",
      step3Title: "Tú abres",
      step3Text: "Recibes tu jar y puedes conseguir upgrades raros.",
      limitedCollection: "COLECCIÓN LIMITADA",
      dropText: "Drop especial disponible por tiempo limitado.",
      seeDrop: "Ver drop",
      exploreCollection: "Explora la colección",
      categoryCards: {
        all: {
          title: "Todos los jars",
          description: "Ver la colección completa.",
        },
        romantic: {
          title: "Romántico & pastel",
          description: "Estilos delicados y femeninos.",
        },
        bold: {
          title: "Statement & glam",
          description: "Brillo y energía.",
        },
      },
      bestsellers: {
        soft: "Un universo delicado con tonos pastel.",
        golden: "Elegancia dorada sofisticada.",
        dark: "Un vibe misterioso y dramático.",
      },
    },
    footer: {
      subscribe: "Suscríbete a nuestros emails",
      emailPlaceholder: "Email",
      followShop: "Síguenos",
      country: "País/región",
      language: "Idioma",
      copyright: "© 2026 Glow Jar",
    },
    collection: {
      title: "Todos los jars",
      subtitle:
        "Explora toda la colección Glow Jar y descubre el estilo que mejor va contigo.",
      filterLabel: "Filtrar por estilo",
      imagePlaceholder: "Imagen jar",
      discover: "Descubrir",
      from: "desde",
      filters: {
        all: "Todos",
        romantic: "Romántico & pastel",
        minimal: "Minimal & clean",
        elegant: "Elegante & clásico",
        bold: "Bold & statement",
        dark: "Oscuro & misterioso",
        boho: "Boho & natural",
        glam: "Glam & festivo",
        celestial: "Celestial & dreamy",
        vintage: "Inspiración vintage",
      },
      items: {
        soft: { description: "Un universo delicado con acentos románticos y tonos pastel." },
        golden: { description: "Una estética inspirada en la elegancia dorada y sofisticada." },
        minimal: { description: "Líneas limpias y refinamiento discreto para un estilo moderno." },
        dark: { description: "Un ambiente profundo y misterioso con acentos dramáticos." },
        rose: { description: "Una estética luminosa y romántica inspirada en tonos rosados delicados." },
        celestial: { description: "Inspirado en el cielo nocturno y la luz sutil de las estrellas." },
        vintage: { description: "Una mezcla de clásico y moderno con influencias retro." },
        cottage: { description: "Una atmósfera natural y romántica inspirada en flores." },
        edgy: { description: "Un estilo audaz y moderno con energía urbana." },
        boho: { description: "Una estética libre y artística con influencias bohemias." },
        glam: { description: "Brillo y opulencia para un estilo que irradia confianza." },
      },
    },
  },
}

const i18n = createI18n({
  legacy: false,
  locale: localStorage.getItem("locale") || "ro",
  fallbackLocale: "en",
  messages,
})

export default i18n