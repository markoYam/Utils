package com.myam.utils.base;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UtilsProyect {

    public boolean isEmptyList(List<?> mList) {
        return mList != null && mList.size() > 0;
    }

    public static String ConvertModelToStringGson(Object mObject) {
        try {
            Gson gson = new Gson();
            return gson.toJson(mObject);
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    /**
     * Valor esperado
     *
     * @param mDate
     * @return
     */
    public static String ConvertToShortDateString(Date mDate) {
        String fecha = null;
        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {

            fecha = mDateFormat.format(mDate);
        } catch (Exception e) {
            Log.e("fecha", e.getMessage() + mDate);
            fecha = "No Asignado";
        }
        return fecha;
    }

    /**
     * @param mDate valor a convertir
     * @return
     */
    public static String ConvertToLargeDateString(Date mDate) {
        String fecha = null;
        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        try {
            fecha = mDateFormat.format(mDate);

        } catch (Exception e) {
            Log.e("ConvertToLargeDate", e.getMessage() + mDate);
            fecha = null;
        }
        return fecha;
    }

    /**
     * Valor esperado date, para obtener el dia
     *
     * @param mDate
     * @return
     */
    public static String getDaysOfDate(Date mDate) {
        try {
            return String.valueOf(android.text.format.DateFormat.format("EEEE", mDate));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Realizar una busqueda sobre cualquier lista
     */
    public static class SearchList extends AsyncTask<Void, Void, Void> {
        private ResultSearch mResultSearch;
        private HandleSearch mHandleSearch;
        private List<?> mObjects;
        private String criterio;
        List<Object> resultado = new ArrayList<>();

        public void setmHandleSearch(HandleSearch mHandleSearch) {
            this.mHandleSearch = mHandleSearch;
        }

        public void setmResultSearch(ResultSearch mResultSearch) {
            this.mResultSearch = mResultSearch;
        }

        public void setdata(List<?> mObjects, String criterio) {
            this.mObjects = mObjects;
            this.criterio = criterio;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < mObjects.size(); i++) {
                if (mHandleSearch != null) {
                    if (mHandleSearch.handle(mObjects.get(i), criterio)) {
                        resultado.add(mObjects.get(i));
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mResultSearch != null) mResultSearch.resultSearch(resultado);
        }
    }

    public interface ResultSearch {
        void resultSearch(List<?> mObjects);
    }

    public interface HandleSearch {
        boolean handle(Object object, String criterio);
    }

    /**
     * Parsear el texto para quitarle los caracteres especiales.
     *
     * @param criterio
     * @return
     */
    public static String ParseStringASCII(String criterio) {
        return
                Normalizer
                        .normalize(criterio, Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "");
    }
}
