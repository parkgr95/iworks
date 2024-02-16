import { getAccessToken } from './auth'

const API_URL = 'https://suhyeon.site/api'

const REQUEST_HEADER = {
  headers: {
    Authorization: 'Bearer ' + getAccessToken(),
  },
}

function formDataToRequestData(formData: FormData): object {
  const requestData: Record<string, string> = {}

  formData.forEach((value, key) => {
    requestData[key] = value.toString()
  })

  return requestData
}

export { API_URL, REQUEST_HEADER, formDataToRequestData }
