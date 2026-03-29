const API_BASE = 'http://localhost:8080/api'

export async function getProducts(params: Record<string, string | number | boolean> = {}) {
  const searchParams = new URLSearchParams()

  Object.entries(params).forEach(([key, value]) => {
    if (value !== '' && value !== null && value !== undefined) {
      searchParams.append(key, String(value))
    }
  })

  const response = await fetch(`${API_BASE}/products?${searchParams.toString()}`)

  if (!response.ok) {
    throw new Error('Failed to fetch products')
  }

  return await response.json()
}

export async function getProductBySlug(slug: string) {
  const response = await fetch(`${API_BASE}/products/${slug}`)

  if (!response.ok) {
    throw new Error('Failed to fetch product')
  }

  return await response.json()
}