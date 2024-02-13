const API_URL = 'https://suhyeon.site/api'

function formDataToRequestData(formData: FormData): object {
  const requestData: Record<string, string> = {}

  formData.forEach((value, key) => {
    requestData[key] = value.toString()
  })

  return requestData
}

export { API_URL, formDataToRequestData }
