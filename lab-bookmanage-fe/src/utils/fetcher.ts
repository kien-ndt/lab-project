export const fetcher = async (
  urlPath: string,
  method: RequestInit['method'],
  requestBody?: RequestInit['body'],
  headers?: RequestInit['headers'],
): Promise<any> => {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_HOST}${urlPath}`, {
    method: method,
    body: requestBody,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      ...headers,
    },
    credentials: 'include',
  });
  const jsonBody = res.ok ? await res.json() : undefined;

  if (res.status >= 400) {
    const err = { status: res.status, errBody: jsonBody };
    throw err;
  }
  return jsonBody;
};
