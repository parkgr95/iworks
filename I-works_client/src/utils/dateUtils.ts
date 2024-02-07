interface DateUtils {
  formatDate(dateTime: string | undefined): string;
  formatDateTime(dateTime: string | undefined): string;
}

const dateUtils: DateUtils = {
  formatDate(dateTime: string | undefined): string {
    if (!dateTime) return ''; // undefined인 경우 빈 문자열 반환
    const date = new Date(dateTime);
    return `${date.getFullYear()}.${(date.getMonth() + 1).toString().padStart(2, '0')}.${date.getDate().toString().padStart(2, '0')}`;
  },
  formatDateTime(dateTime: string | undefined): string {
    if (!dateTime) return ''; // undefined인 경우 빈 문자열 반환
    const date = new Date(dateTime);
    return `${date.getFullYear()}.${(date.getMonth() + 1).toString().padStart(2, '0')}.${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;
  }
};

export default dateUtils;