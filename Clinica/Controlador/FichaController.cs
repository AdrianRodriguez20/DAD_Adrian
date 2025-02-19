﻿using Modelo;
using System;
using System.Collections.Generic;

namespace Controlador
{
    public class FichaController
    {
        FichaDAO fichaDAO = new FichaDAO(new GestorFichero("fichaspacientes.txt"));

        /// <summary>
        /// Función que devuelve una lista de fichas por NHC
        /// </summary>
        /// <param name="contenido">NHC del paciente</param>
        /// <returns>Lista de fichas</returns>
        public List<string[]> listarFichasNhc(String contenido)
        {
            List<Ficha> fichas = null;
          
                fichas = fichaDAO.findByNhc(Convert.ToInt32(contenido));
          

            List<string[]> pacientesStr = new List<string[]>(); ;
            if (fichas != null)
            {
                foreach (Ficha ficha in fichas)
                {
                    pacientesStr.Add(new string[] { ficha.FechaVisita , ficha.Motivo ,ficha.Preinscripción });

                }
            }

            return pacientesStr;
        }
    }
}