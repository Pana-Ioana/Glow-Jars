export interface Mood {
  id: string
  title: string
  description: string
  basePrice: number
  color: string
}

export const moods: Mood[] = [
  {
    id: "soft-girl",
    title: "Soft Girl",
    description: "Pearls, blush tones, delicate charms.",
    basePrice: 89,
    color: "#F8E8EE",
  },
  {
    id: "golden-muse",
    title: "Golden Muse",
    description: "Warm gold accents, classy shine.",
    basePrice: 119,
    color: "#F4E3C1",
  },
  {
    id: "minimal-glow",
    title: "Minimal Glow",
    description: "Clean lines, subtle sparkle.",
    basePrice: 99,
    color: "#F2F2F2",
  },
  {
    id: "dark-romance",
    title: "Dark Romance",
    description: "Moody elegance, deep tones.",
    basePrice: 109,
    color: "#E8E0EC",
  },
  {
    id: "rose-quartz",
    title: "Rose Quartz",
    description: "Soft pink crystals and romantic glow.",
    basePrice: 129,
    color: "#FDE2E4",
  },
  {
    id: "celestial-glow",
    title: "Celestial Glow",
    description: "Stars, moons, dreamy silver accents.",
    basePrice: 119,
    color: "#E6ECF5",
  },
  {
    id: "vintage-muse",
    title: "Vintage Muse",
    description: "Antique gold, classic elegance.",
    basePrice: 139,
    color: "#EFE4D8",
  },
  {
    id: "clean-girl",
    title: "Clean Girl Aesthetic",
    description: "Simple hoops, minimal gold, everyday glow.",
    basePrice: 95,
    color: "#F5EFE6",
  },
  {
    id: "pearl-dream",
    title: "Pearl Dream",
    description: "Modern pearls with soft shimmer.",
    basePrice: 125,
    color: "#FDFBF7",
  },
  {
    id: "summer-spritz",
    title: "Summer Spritz",
    description: "Colorful accents, playful summer vibe.",
    basePrice: 85,
    color: "#FFF3E0",
  },
  {
    id: "midnight-muse",
    title: "Midnight Muse",
    description: "Dark crystals and night sparkle.",
    basePrice: 149,
    color: "#E4E6F2",
  },
  {
    id: "cottage-bloom",
    title: "Cottage Bloom",
    description: "Floral charms and soft romantic details.",
    basePrice: 105,
    color: "#F3EFEA",
  },
]